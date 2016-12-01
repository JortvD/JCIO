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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import nl.jortenmilo.keyboard.KeyboardInput;
import nl.jortenmilo.main.CloseManager;
import nl.jortenmilo.mouse.MouseInput;
import nl.jortenmilo.settings.SettingsManager;
import nl.jortenmilo.utils.SystemUtils;

public class Console {
	
	private static boolean inited = false;
	private static JFrame frame;
	private static JTextArea t;
	private static ConsoleInputStream cis;
	private static ConsolePrintStream cps;
	private static BufferedWriter bw;
	private static KeyboardInput ki;
	private static MouseInput mi;
	private static List<ConsoleEventListener> wels = new ArrayList<ConsoleEventListener>();
	private static SettingsManager settings;
	
	public static PrintStream dout; //DEBUG
	public static InputStream din; //DEBUG
	
	public static void init() {
		if(!inited) {
			inited = true;
			
			frame = new JFrame();
			frame.setTitle("JCIO");
			frame.setSize(1600, 800);
			frame.setResizable(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			
			ki = new KeyboardInput();
			mi = new MouseInput();
			
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
			
			frame.addKeyListener(ki);
			t.addMouseListener(mi);
			t.addMouseMotionListener(mi);
			t.addMouseWheelListener(mi);
			
			frame.addComponentListener(new ComponentListener() {
				@Override
				public void componentResized(ComponentEvent e) {
					p.setBounds(0, 0, frame.getWidth(), frame.getHeight());
					s.setBounds(0, 0, p.getWidth()-16, p.getHeight()-46);
					t.setBounds(0, 0, s.getWidth(), s.getHeight());
					frame.repaint();
					
					for(ConsoleEventListener wel : wels) {
						ConsoleResizedEvent event = new ConsoleResizedEvent();
						event.setWidth(e.getComponent().getWidth());
						event.setHeight(e.getComponent().getHeight());
						event.setX(e.getComponent().getX());
						event.setY(e.getComponent().getY());
						
						try {
							wel.onResized(event);
						} catch(Error | Exception e2) {
							new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
						}
					}
				}
				@Override
				public void componentHidden(ComponentEvent e) {
					for(ConsoleEventListener wel : wels) {
						ConsoleHiddenEvent event = new ConsoleHiddenEvent();
						event.setWidth(e.getComponent().getWidth());
						event.setHeight(e.getComponent().getHeight());
						event.setX(e.getComponent().getX());
						event.setY(e.getComponent().getY());
						
						try {
							wel.onHidden(event);
						} catch(Error | Exception e2) {
							new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
						}
					}
				}
				@Override
				public void componentMoved(ComponentEvent e) {
					for(ConsoleEventListener wel : wels) {
						ConsoleMovedEvent event = new ConsoleMovedEvent();
						event.setWidth(e.getComponent().getWidth());
						event.setHeight(e.getComponent().getHeight());
						event.setX(e.getComponent().getX());
						event.setY(e.getComponent().getY());
						wel.onMoved(event);
						
						try {
							wel.onMoved(event);
						} catch(Error | Exception e2) {
							new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
						}
					}
				}
				@Override
				public void componentShown(ComponentEvent e) {
					for(ConsoleEventListener wel : wels) {
						ConsoleShownEvent event = new ConsoleShownEvent();
						event.setWidth(e.getComponent().getWidth());
						event.setHeight(e.getComponent().getHeight());
						event.setX(e.getComponent().getX());
						event.setY(e.getComponent().getY());
						wel.onShown(event);
						
						try {
							wel.onShown(event);
						} catch(Error | Exception e2) {
							new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
						}
					}
				}
			});
			frame.addWindowListener(new WindowListener() {
				@Override
				public void windowActivated(WindowEvent e) {}
				@Override
				public void windowClosed(WindowEvent e) {
					
				}
				@Override
				public void windowClosing(WindowEvent e) {
					for(ConsoleEventListener wel : wels) {
						ConsoleClosedEvent event = new ConsoleClosedEvent();
						event.setWidth(e.getComponent().getWidth());
						event.setHeight(e.getComponent().getHeight());
						event.setX(e.getComponent().getX());
						event.setY(e.getComponent().getY());
						
						try {
							wel.onClosed(event);
						} catch(Error | Exception e2) {
							new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
						}
					}
					
					CloseManager.close();
				}
				@Override
				public void windowDeactivated(WindowEvent e) {}
				@Override
				public void windowDeiconified(WindowEvent e) {}
				@Override
				public void windowIconified(WindowEvent e) {}
				@Override
				public void windowOpened(WindowEvent e) {
					for(ConsoleEventListener wel : wels) {
						ConsoleOpenedEvent event = new ConsoleOpenedEvent();
						event.setWidth(e.getComponent().getWidth());
						event.setHeight(e.getComponent().getHeight());
						event.setX(e.getComponent().getX());
						event.setY(e.getComponent().getY());
						
						try {
							wel.onOpened(event);
						} catch(Error | Exception e2) {
							new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
						}
					}
				}
			});
			frame.getContentPane().add(p);
			frame.repaint();
			
			dout = System.out;
			din = System.in;
			
			ConsoleOutputStream cos = new ConsoleOutputStream();
			cps = new ConsolePrintStream(cos);
			cis = new ConsoleInputStream(cos);
			frame.addKeyListener(cis);
			
			if(new File("logs").exists()) {
				File f = new File("logs/" + new SimpleDateFormat("MM-dd-yyyy HH-mm-ss").format(System.currentTimeMillis()) + ".log");
				try {
					f.createNewFile();
					bw = new BufferedWriter(new PrintWriter(f));
				} catch(Error | Exception e) {
					new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
				}
			}
		} else {
			Console.println(ConsoleUser.Error, "Console is already created!");
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
			int l = t.getGraphics().getFontMetrics().stringWidth(lineText)+12;
			String text = new String(new byte[]{(byte)b});
			
			if(settings.contains("log")) {
				if(settings.get("log").equals("true") && bw!=null) {
					bw.write(b);
				}
			}
			
			if((l > t.getWidth()) && !text.equals("\n")) {
				t.append("\n");
				lineText = "";
			}
			
			if(text.equals("\n")) {
				t.append("\n");
				lineText = "";
				fullLine = "";
				return;
			}
			
			lineText += text;
			fullLine += text;
			t.append(text);
		}
		
		public void println(String s) throws IOException {
			byte[] bytes = s.getBytes();
			for(int i = 0; i < s.length(); i++) {
				write(bytes[i]);
			}
			write((byte)'\n');
		}
		
		public void print(String s) throws IOException {
			byte[] bytes = s.getBytes();
			for(int i = 0; i < s.length(); i++) {
				write(bytes[i]);
			}
		}
	}
	
	static class ConsoleInputStream implements KeyListener {
		
		private ConsoleOutputStream cos;
		private int presses = 0;
		
		private String waitText = "";
		private Object lock = new Object();
		
		private ConsoleInputStream(ConsoleOutputStream cos) {
			 this.cos = cos;
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
					Console.println(ConsoleUser.Error, "Unknown Error: " + e1.getMessage());
				} catch (UnsupportedFlavorException e1) {
					Console.println(ConsoleUser.Error, "Unknown Error: " + e1.getMessage());
				} catch (IOException e1) {
					Console.println(ConsoleUser.Error, "Unknown Error: " + e1.getMessage());
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
		}
		
		public String waitUntilDone() {
			waitText = "";
			Waiting = true;
			synchronized (lock) {
			    while (!WakeupNeeded) {
			        try {
						lock.wait();
					} catch(Error | Exception e) {
						new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
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
		update();
		if(!settings.contains("time")) {
			String time = SystemUtils.getTime();
			
			if(user.equals(ConsoleUser.System)) {
				cps.println("[SYS " + time + "]: " + s);
			}
			else if(user.equals(ConsoleUser.User)) {
				cps.println("[YOU " + time + "]: " + s);
			}
			else if(user.equals(ConsoleUser.Error)) {
				cps.println("[ERR " + time + "]: " + s);
			}
			else if(user.equals(ConsoleUser.Empty)) {
				cps.println("[" + time + "]: " + s);
			}
			return;
		}
		
		if(settings.get("time").equals("true")) {
			String time = SystemUtils.getTime();
			
			if(user.equals(ConsoleUser.System)) {
				cps.println("[SYS " + time + "]: " + s);
			}
			else if(user.equals(ConsoleUser.User)) {
				cps.println("[YOU " + time + "]: " + s);
			}
			else if(user.equals(ConsoleUser.Error)) {
				cps.println("[ERR " + time + "]: " + s);
			}
			else if(user.equals(ConsoleUser.Empty)) {
				cps.println("[" + time + "]: " + s);
			}
		}
		else if(settings.get("time").equals("false")) {
			if(user == ConsoleUser.System) {
				cps.println("[SYS]: " + s);
			}
			else if(user == ConsoleUser.User) {
				cps.println("[YOU]: " + s);
			}
			else if(user == ConsoleUser.Error) {
				cps.println("[ERR]: " + s);
			}
			else if(user == ConsoleUser.Empty) {
				cps.println("[]: " + s);
			}
		}
	}
	
	public static void println(String s) {
		update();
		if(!settings.contains("time")) {
			cps.println("[SYS " + SystemUtils.getTime() + "]: " + s);
			return;
		}
		
		if(settings.get("time").equals("true")) {
			cps.println("[SYS " + SystemUtils.getTime() + "]: " + s);
		}
		else if(settings.get("time").equals("false")) {
			cps.println("[SYS]: " + s);
		}
	}
	
	public static void write(String s) {
		update();
		cps.print(s);
	}
	
	public static void writeln(String s) {
		update();
		cps.println(s);
	}
	
	public static String readln() {
		update();
		if(settings.get("time").equals("true")) {
			cps.print("[YOU " + SystemUtils.getTime() + "]: ");
			return cis.waitUntilDone();
		}
		else if(settings.get("time").equals("false")) {
			cps.print("[YOU]: ");
			return cis.waitUntilDone();
		}
		
		return "";
	}
	
	public static String read() {
		update();
		return cis.waitUntilDone();
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
	
	public static void update() {
		
	}

	public static void close() throws IOException {
		bw.close();
	}

	public static KeyboardInput getKeyboardInput() {
		return ki;
	}
	
	public static MouseInput getMouseInput() {
		return mi;
	}

	protected static void addEventListener(ConsoleEventListener e) {
		wels.add(e);
	}

	public static void setSettingsManager(SettingsManager settings) {
		Console.settings = settings;
	}
	
}
