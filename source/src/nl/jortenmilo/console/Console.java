package nl.jortenmilo.console;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import nl.jortenmilo.input.KeyboardInput;
import nl.jortenmilo.settings.Settings;
import nl.jortenmilo.utils.SystemUtils;

public class Console {
	
	private static boolean inited = false;
	private static JFrame frame;
	private static JTextArea t;
	private static PrintStream d;
	private static ConsoleInputStream cis;
	
	public static void init() {
		if(!inited) {
			inited = true;
			
			frame = new JFrame();
			frame.setTitle("JCIO");
			frame.setSize(1600, 800);
			frame.setResizable(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			
			KeyboardInput ki = new KeyboardInput();
			frame.addKeyListener(ki);
			
			JPanel p = new JPanel();
			p.setSize(frame.getWidth(), frame.getHeight());
			p.setLayout(null);
			p.setLocation(0, 0);
			p.setBackground(Color.BLACK);
			
			t = new JTextArea();
			JScrollPane s = new JScrollPane(t);
			s.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			s.setBounds(0, 0, p.getWidth()-16, p.getHeight()-46);
			t.setEditable(false);
			t.setBounds(0, 0, s.getWidth(), s.getHeight());
			t.setForeground(Color.LIGHT_GRAY);
			t.setBackground(Color.BLACK);
			t.setFont(new Font("Consolas", Font.BOLD, 20));
			t.setFocusable(false);
			p.add(s);
			
			frame.addComponentListener(new ComponentListener() {
				@Override
				public void componentResized(ComponentEvent e) {
					p.setBounds(0, 0, frame.getWidth(), frame.getHeight());
					s.setBounds(0, 0, p.getWidth()-16, p.getHeight()-46);
					t.setBounds(0, 0, s.getWidth(), s.getHeight());
					frame.repaint();
				}
				@Override
				public void componentHidden(ComponentEvent arg0) {}
				@Override
				public void componentMoved(ComponentEvent arg0) {}
				@Override
				public void componentShown(ComponentEvent arg0) {}
			});
			frame.getContentPane().add(p);
			frame.repaint();
			
			d = System.out;
			
			ConsoleOutputStream cos = new ConsoleOutputStream();
			ConsolePrintStream cps = new ConsolePrintStream(cos);
			cis = new ConsoleInputStream(cos);
			System.setOut(cps);
			System.setErr(cps);
			frame.addKeyListener(cis);
			System.setIn(cis);
		} else {
			Console.println(ConsoleUser.Error, "Console is already inited!");
		}
	}
	
	static class ConsolePrintStream extends PrintStream {

		private ConsolePrintStream(OutputStream out) {
			super(out);
		}
	}
	
	static class ConsoleOutputStream extends OutputStream {
		
		private String lineText = "";
		private String fullLine = "";
		
		private ConsoleOutputStream() {}
		
		@Override
		public void write(int b) throws IOException {
			int l = (int)(t.getFont().getStringBounds(lineText, new FontRenderContext(new AffineTransform(),true,true)).getWidth()+30);
			
			if((l > t.getWidth()) && !new String(new byte[]{(byte)b}).equals("\n")) {
				t.append("\n");
				lineText = "";
			}
			
			if(new String(new byte[]{(byte)b}).equals("\n")) {
				t.append("\n");
				lineText = "";
				fullLine = "";
				return;
			}
			
			lineText += new String(new byte[]{(byte)b});
			fullLine += new String(new byte[]{(byte)b});
			t.append(new String(new byte[]{(byte)b}));
		}
	}
	
	static class ConsoleInputStream extends InputStream implements KeyListener {
		
		private ArrayBlockingQueue<Integer> queue;
		private ConsoleOutputStream cos;
		private int presses = 0;
		
		private String waitText = "";
		private Object lock = new Object();
		
		private ConsoleInputStream(ConsoleOutputStream cos) {
			 queue = new ArrayBlockingQueue<Integer>(1024);
			 this.cos = cos;
		}
		
		@Override
		public int read() throws IOException {
			Integer i = null;
			
			try {
				i = queue.take();
			} catch (InterruptedException ex) {
				Console.println(ConsoleUser.Error, "Error: InterruptedException");
			}
			
			if(i != null)
				return i;
			return -1;
		}
		
		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			if (b == null) {
				throw new NullPointerException();
			} else if (off < 0 || len < 0 || len > b.length - off) {
				throw new IndexOutOfBoundsException();
			} else if (len == 0) {
				return 0;
			}
			
			int c = read();
			
			if (c == -1) {
				return -1;
			}
			
			b[off] = (byte)c;
			int i = 1;
			
			try {
				for (; i < len && available() > 0 ; i++) {
					c = read();
					if (c == -1) {
						break;
					}
					b[off + i] = (byte)c;
				}
			} catch (IOException ee) {}
			
			return i;
		}
		
		@Override
		public int available(){
			return queue.size();
		}

		@Override
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}
		
		@Override
		public void keyTyped(KeyEvent e) {
			int c = e.getKeyChar();
			
			if((byte)e.getKeyChar()==22) {
				try {
					String text = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
					Console.write(text);
					presses += text.length();
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (UnsupportedFlavorException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return;
			}
			
			if((byte)e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
				if(presses > 0) {
					t.setText(t.getText().substring(0,t.getText().length()-1));
					
					if(waitText.length() > 0) {
						waitText = waitText.substring(0, waitText.length()-1);
					}
					
					c = 8;
					presses--;
					
					if(cos.fullLine.length() != 0) {
						cos.lineText = cos.lineText.substring(0, cos.lineText.length()-1);
					}
					
					if(cos.fullLine.length() != 0 && cos.lineText.length() == 0) {
						cos.lineText = cos.fullLine;
						t.setText(t.getText().substring(0,t.getText().length()-1));
						cos.lineText = cos.lineText.substring(0, cos.lineText.length()-1);
						cos.fullLine = cos.fullLine.substring(0, cos.fullLine.length()-1);
					} else {
						cos.fullLine = cos.fullLine.substring(0, cos.fullLine.length()-1);
					}
				}
			} else {
				presses++;
				if(Waiting) {
					Console.write(Character.toString(e.getKeyChar()));
				}
			}
			
			if((byte)e.getKeyChar() == KeyEvent.VK_ENTER) {
				presses = 0;
				synchronized (lock) {
					WakeupNeeded = true;
				    lock.notifyAll();
				}
			}
			else if(c!=8) {
				waitText += new String(new char[]{(char) c});
			}
			
			try {
				queue.put(c);
				
			} catch (InterruptedException ex) {
				Console.println(ConsoleUser.Error, "Error: InterruptedException");
			}
		}
		
		public String waitUntilDone() {
			waitText = "";
			Waiting = true;
			synchronized (lock) {
			    while (!WakeupNeeded) {
			        try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			    }
			}
			WakeupNeeded = false;
			Waiting = false;
			String tts = waitText;
			waitText = "";
			return tts;
		}
		
		private boolean WakeupNeeded = false;
		private boolean Waiting = false;
	}
	
	public static void println(String user, String s) {
		if(!Settings.contains("time")) {
			String time = SystemUtils.getTime();
			
			if(user.equals(ConsoleUser.System)) {
				System.out.println("[SYS " + time + "]: " + s);
			}
			else if(user.equals(ConsoleUser.User)) {
				System.out.println("[YOU " + time + "]: " + s);
			}
			else if(user.equals(ConsoleUser.Error)) {
				System.out.println("[ERR " + time + "]: " + s);
			}
			else if(user.equals(ConsoleUser.Empty)) {
				System.out.println("[" + time + "]: " + s);
			}
			return;
		}
		
		if(Settings.get("time").equals("true")) {
			String time = SystemUtils.getTime();
			
			if(user.equals(ConsoleUser.System)) {
				System.out.println("[SYS " + time + "]: " + s);
			}
			else if(user.equals(ConsoleUser.User)) {
				System.out.println("[YOU " + time + "]: " + s);
			}
			else if(user.equals(ConsoleUser.Error)) {
				System.out.println("[ERR " + time + "]: " + s);
			}
			else if(user.equals(ConsoleUser.Empty)) {
				System.out.println("[" + time + "]: " + s);
			}
		}
		else if(Settings.get("time").equals("false")) {
			if(user == ConsoleUser.System) {
				System.out.println("[SYS]: " + s);
			}
			else if(user == ConsoleUser.User) {
				System.out.println("[YOU]: " + s);
			}
			else if(user == ConsoleUser.Error) {
				System.out.println("[ERR]: " + s);
			}
			else if(user == ConsoleUser.Empty) {
				System.out.println("[]: " + s);
			}
		}
	}
	
	public static void println(String s) {
		if(!Settings.contains("time")) {
			System.out.println("[SYS " + SystemUtils.getTime() + "]: " + s);
			return;
		}
		
		if(Settings.get("time").equals("true")) {
			System.out.println("[SYS " + SystemUtils.getTime() + "]: " + s);
		}
		else if(Settings.get("time").equals("false")) {
			System.out.println("[SYS]: " + s);
		}
	}
	
	public static void write(String s) {
		System.out.print(s);
	}
	
	public static void writeln(String s) {
		System.out.println(s);
	}
	
	public static String readln() {
		Scanner scr = new Scanner(System.in);
		
		if(Settings.get("time").equals("true")) {
			System.out.print("[YOU " + SystemUtils.getTime() + "]: ");
			//return scr.nextLine();
			return cis.waitUntilDone();
		}
		else if(Settings.get("time").equals("false")) {
			System.out.print("[YOU]: ");
			return scr.nextLine();
		}
		
		return "";
	}
	
	public static String read() {
		Scanner scr = new Scanner(System.in);
		return scr.nextLine();
	}
	
	public static class ConsoleUser {
		public static String System = "SYS";
		public static String User = "YOU";
		public static String Empty = "0";
		public static String Error = "ERR";
	}

	public static void clear() {
		t.setText("");
	}
	
}
