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
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;
import nl.jortenmilo.keyboard.KeyboardInput;
import nl.jortenmilo.main.CloseManager;
import nl.jortenmilo.mouse.MouseInput;
import nl.jortenmilo.settings.SettingsManager;
import nl.jortenmilo.utils.ObjectUtils;
import nl.jortenmilo.utils.SystemUtils;

public class Console {
	
	private static boolean inited = false;
	private static JFrame frame;
	private static JTextPane t;
	private static ConsoleInputStream cis;
	private static ConsolePrintStream cps;
	private static BufferedWriter bw;
	private static KeyboardInput ki;
	private static MouseInput mi;
	private static SettingsManager settings;
	private static StyledDocument d;
	private static ConsoleOutputStream cos;
	private static SimpleAttributeSet at;
	private static EventManager events;
	
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
			
			t = new JTextPane();
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
					
					ConsoleResizedEvent event = new ConsoleResizedEvent();
					event.setWidth(e.getComponent().getWidth());
					event.setHeight(e.getComponent().getHeight());
					event.setX(e.getComponent().getX());
					event.setY(e.getComponent().getY());
					
					for(EventHandler handler : events.getHandlers(event.getClass())) {
						handler.execute(event);
					}
				}
				@Override
				public void componentHidden(ComponentEvent e) {
					ConsoleHiddenEvent event = new ConsoleHiddenEvent();
					event.setWidth(e.getComponent().getWidth());
					event.setHeight(e.getComponent().getHeight());
					event.setX(e.getComponent().getX());
					event.setY(e.getComponent().getY());
					
					for(EventHandler handler : events.getHandlers(event.getClass())) {
						handler.execute(event);
					}
				}
				@Override
				public void componentMoved(ComponentEvent e) {
					ConsoleMovedEvent event = new ConsoleMovedEvent();
					event.setWidth(e.getComponent().getWidth());
					event.setHeight(e.getComponent().getHeight());
					event.setX(e.getComponent().getX());
					event.setY(e.getComponent().getY());
					
					for(EventHandler handler : events.getHandlers(event.getClass())) {
						handler.execute(event);
					}
				}
				@Override
				public void componentShown(ComponentEvent e) {
					ConsoleShownEvent event = new ConsoleShownEvent();
					event.setWidth(e.getComponent().getWidth());
					event.setHeight(e.getComponent().getHeight());
					event.setX(e.getComponent().getX());
					event.setY(e.getComponent().getY());
					
					for(EventHandler handler : events.getHandlers(event.getClass())) {
						handler.execute(event);
					}
				}
			});
			frame.addWindowListener(new WindowListener() {
				@Override
				public void windowActivated(WindowEvent e) {}
				@Override
				public void windowClosed(WindowEvent e) {}
				@Override
				public void windowClosing(WindowEvent e) {
					ConsoleClosedEvent event = new ConsoleClosedEvent();
					event.setWidth(e.getComponent().getWidth());
					event.setHeight(e.getComponent().getHeight());
					event.setX(e.getComponent().getX());
					event.setY(e.getComponent().getY());
					
					for(EventHandler handler : events.getHandlers(event.getClass())) {
						handler.execute(event);
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
					ConsoleOpenedEvent event = new ConsoleOpenedEvent();
					event.setWidth(e.getComponent().getWidth());
					event.setHeight(e.getComponent().getHeight());
					event.setX(e.getComponent().getX());
					event.setY(e.getComponent().getY());
					
					for(EventHandler handler : events.getHandlers(event.getClass())) {
						handler.execute(event);
					}
				}
			});
			
			frame.getContentPane().add(p);
			frame.repaint();
			
			dout = System.out;
			din = System.in;
			
			d = t.getStyledDocument();
			
			cos = new ConsoleOutputStream();
			cps = new ConsolePrintStream(cos);
			cis = new ConsoleInputStream(cos);
			frame.addKeyListener(cis);
			
			if(new File("logs").exists()) {
				File f = new File("logs/" + new SimpleDateFormat("MM-dd-yyyy HH-mm-ss").format(System.currentTimeMillis()) + ".log");
				try {
					f.createNewFile();
					bw = new BufferedWriter(new PrintWriter(f));
				} catch(Error | Exception e) {
					new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
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
		
		private List<Integer> brakes = new ArrayList<Integer>();
		
		private ConsoleOutputStream() {
			at = new SimpleAttributeSet();
		}
		
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
				brakes.add(d.getLength());
				
				try {d.insertString(d.getLength(), "\n", at);}
				catch (BadLocationException e) {}
				
				lineText = "";
			}
			
			if(text.equals("\n")) {
				brakes.add(d.getLength());
				
				try {d.insertString(d.getLength(), "\n", at);}
				catch (BadLocationException e) {}
				
				lineText = "";
				fullLine = "";
				return;
			}
			
			lineText += text;
			fullLine += text;
			
			try {d.insertString(d.getLength(), text, at);}
			catch (BadLocationException e) {}
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
			
			if((byte)e.getKeyChar() == 22) {
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
					try {d.remove(d.getLength()-1, 1);}
					catch (BadLocationException e2) {}
					
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
						try {d.remove(d.getLength()-1, 1);}
						catch (BadLocationException e2) {}
						cos.lineText = cos.lineText.substring(0, cos.lineText.length()-1);
						cos.fullLine = cos.fullLine.substring(0, cos.fullLine.length()-1);
					} else {
						cos.fullLine = cos.fullLine.substring(0, cos.fullLine.length()-1);
					}
				}
			} 
			else if((byte)e.getKeyChar() == KeyEvent.VK_ENTER) {
				presses = 0;
				Console.write("\n");
				synchronized (lock) {
					WakeupNeeded = true;
				    lock.notifyAll();
				}
			}
			else {
				presses++;
				if(Waiting) {
					Console.write(Character.toString(e.getKeyChar()));
					waitText += new String(new char[]{(char) c});
				}
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
						new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
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
		
		if(settings == null) {
			String time = new SystemUtils().getTime();
			
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
		
		if(!settings.contains("time")) {
			String time = new SystemUtils().getTime();
			
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
			String time = new SystemUtils().getTime();
			
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
		if(!settings.contains("time")) {
			cps.println("[SYS " + new SystemUtils().getTime() + "]: " + s);
			return;
		}
		
		if(settings.get("time").equals("true")) {
			cps.println("[SYS " + new SystemUtils().getTime() + "]: " + s);
		}
		else if(settings.get("time").equals("false")) {
			cps.println("[SYS]: " + s);
		}
	}
	
	public static void write(String s) {
		cps.print(s);
	}
	
	public static void writeln(String s) {
		cps.println(s);
	}
	
	public static String readln() {
		if(settings.get("time").equals("true")) {
			cps.print("[YOU " + new SystemUtils().getTime() + "]: ");
			return cis.waitUntilDone();
		}
		else if(settings.get("time").equals("false")) {
			cps.print("[YOU]: ");
			return cis.waitUntilDone();
		}
		
		return "";
	}
	
	public static String read() {
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
		
		ConsoleClearedEvent event = new ConsoleClearedEvent();
		event.setHeight(frame.getHeight());
		event.setWidth(frame.getWidth());
		event.setX(frame.getX());
		event.setY(frame.getY());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}
	
	public static void update() {
		if(settings.contains("foreground")) {
			Color fg = new ObjectUtils().StringToColor(settings.get("foreground"));
			if(t.getForeground() != fg) t.setForeground(fg);
		}
		if(settings.contains("background")) {
			Color bg = new ObjectUtils().StringToColor(settings.get("background"));
			if(t.getBackground() != bg) t.setBackground(bg);
		}
		if(settings.contains("default_title")) {
			String dt = settings.get("default_title");
			dt = dt.replace("_", " ");
			if(frame.getTitle() != dt) frame.setTitle(dt);
		}
		if(settings.contains("default_width")) {
			int dw = new ObjectUtils().StringToInteger(settings.get("default_width"));
			if(frame.getWidth() != dw) frame.setSize(dw, frame.getHeight());
		}
		if(settings.contains("default_height")) {
			int dh = new ObjectUtils().StringToInteger(settings.get("default_height"));
			if(frame.getHeight() != dh) frame.setSize(frame.getWidth(), dh);
		}
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

	public static void setSettingsManager(SettingsManager settings) {
		Console.settings = settings;
	}

	public static void setEventManager(EventManager events) {
		Console.events = events;
	}
	
}
