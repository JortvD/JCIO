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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

import nl.jortenmilo.close.CloseManager;
import nl.jortenmilo.error.NonNullableParameterError;
import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;
import nl.jortenmilo.keyboard.KeyboardInput;
import nl.jortenmilo.mouse.MouseInput;
import nl.jortenmilo.settings.SettingsManager;
import nl.jortenmilo.utils.defaults.ObjectUtils;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This is the Console class. It contains all the methods to print text to the console, read from the console and/or debug information.
 */
public class Console {
	
	private static boolean inited = false;
	private static JFrame frame;
	private static JTextPane t;
	private static ConsoleInputStream cis;
	private static ConsolePrintStream cps;
	private static GZIPOutputStream bw;
	private static KeyboardInput ki;
	private static MouseInput mi;
	private static StyledDocument d;
	private static ConsoleOutputStream cos;
	private static SimpleAttributeSet at;
	
	private static EventManager events;
	private static CloseManager close;
	private static SettingsManager settings;
	
	public static PrintStream dout; //DEBUG OUT
	public static InputStream din; //DEBUG IN
	
	/**
	 * The initialization method for the console. This method won't do anything, don't use it. Please.
	 * @throws NullPointerException When something is null but still called
	 */
	public static void init() throws NullPointerException {
		if(!inited) {
			inited = true;
			
			if(new File("logs").exists()) {
				File f = new File("logs/" + new SimpleDateFormat("MM-dd-yyyy HH-mm-ss").format(System.currentTimeMillis()) + ".gzip");
				try {
					
					f.createNewFile();
					//bw = new BufferedWriter(new PrintWriter(f));
					bw = new GZIPOutputStream(new FileOutputStream(f));
					
					debug("----- JCIO -----");
					debug("");
					debug("DATA [OS_NAME][" + System.getProperty("os.name") + "]");
					debug("DATA [OS_ARCH][" + System.getProperty("os.arch") + "]");
					debug("DATA [OS_TIME][" + new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(System.currentTimeMillis()) + "]");
					debug("DATA [OS_VERSION][" + System.getProperty("os.version") + "]");
					debug("DATA [JAVA_NAME][" + System.getProperty("java.name") + "]");
					debug("DATA [JAVA_VERSION][" + System.getProperty("java.version") + "]");
					debug("DATA [JAVA_VENDOR][" + System.getProperty("java.vendor") + "]");
					debug("DATA [JAVA_HOME][" + System.getProperty("java.home") + "]");
					debug("DATA [VM_NAME][" + System.getProperty("java.vm.name") + "]");
					debug("DATA [VM_VERSION][" + System.getProperty("java.vm.version") + "]");
					debug("DATA [VM_VENDOR][" + System.getProperty("java.vm.vendor") + "]");
					debug("DATA [VM_INFO][" + System.getProperty("java.vm.info") + "]");
					debug("DATA [RUNTIME_NAME][" + System.getProperty("java.runtime.name") + "]");
					debug("DATA [RUNTIME_VERSION][" + System.getProperty("java.runtime.version") + "]");
					debug("DATA [RUNTIME_VENDOR][" + System.getProperty("java.runtime.vendor") + "]");
					debug("DATA [SUN_ENCODING][" + System.getProperty("sun.encoding") + "]");
					debug("DATA [SUN_DESKTOP][" + System.getProperty("sun.desktop") + "]");
					debug("DATA [SUN_COMMAND][" + System.getProperty("sun.java.command") + "]");
					debug("DATA [SUN_BOOT][" + System.getProperty("sun.boot.library.path") + "]");
					debug("DATA [SUN_LAUNCHER][" + System.getProperty("sun.java.launcher") + "]");
					debug("DATA [USER_NAME][" + System.getProperty("user.name") + "]");
					debug("DATA [USER_LANGUAGE][" + System.getProperty("user.language") + "]");
					debug("DATA [USER_COUNTRY][" + System.getProperty("user.country") + "]");
					debug("");
				} 
				catch(Exception | Error e) {
					e.printStackTrace();
					new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
				}
			}
			
			frame = new JFrame();
			frame.setTitle("JCIO");
			frame.setSize(1600, 800);
			frame.setResizable(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			
			debug("FRAME [TITLE][" + frame.getTitle() + "]");
			debug("FRAME [SIZE][" + frame.getWidth() + ", " + frame.getHeight() + "]");
			debug("FRAME [RESIZABLE][" + frame.isResizable() + "]");
			debug("FRAME [CLOSE_OPERATION][" + frame.getDefaultCloseOperation() + "]");
			debug("FRAME [VISIBLE][" + frame.isVisible() + "]");
			
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
			s.setAutoscrolls(true);
			s.setWheelScrollingEnabled(true);
			t.setEditable(false);
			t.setBounds(0, 0, s.getWidth(), s.getHeight());
			t.setForeground(Color.LIGHT_GRAY);
			t.setBackground(Color.BLACK);
			t.setFont(new Font("Consolas", Font.BOLD, 20));
			t.setFocusable(false);
			p.add(s);
			
			debug("TEXTPANE [EDITABLE][" + t.isEditable() + "]");
			debug("TEXTPANE [SIZE][" + t.getWidth() + ", " + t.getHeight() + "]");
			debug("TEXTPANE [FOREGROUND][" + t.getForeground().toString() + "]");
			debug("TEXTPANE [BACKGROUND][" + t.getBackground().toString() + "]");
			debug("TEXTPANE [FONT_FAMILY][" + t.getFont().getFontName() + "]");
			debug("TEXTPANE [FONT_STYLE][" + t.getFont().getStyle() + "]");
			debug("TEXTPANE [FONT_SIZE][" + t.getFont().getSize() + "]");
			debug("TEXTPANE [FOCUSABLE][" + t.isFocusable() + "]");
			
			frame.addKeyListener(ki);
			t.addMouseListener(mi);
			t.addMouseMotionListener(mi);
			t.addMouseWheelListener(mi);
			
			frame.addComponentListener(new ComponentListener() {
				@Override
				public void componentResized(ComponentEvent e) {
					debug("CONSOLE_RESIZED [" + new SystemUtils().getTime() + "][" + frame.getWidth() + ", " + frame.getHeight() + "][" + p.getWidth() + ", " + p.getHeight() + "]");
					
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
					debug("CONSOLE_HIDDEN [" + new SystemUtils().getTime() + "]");
					
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
					debug("CONSOLE_MOVED [" + new SystemUtils().getTime() + "][" + frame.getX() + ", " + frame.getY() + "]");
					
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
					debug("CONSOLE_SHOWN [" + new SystemUtils().getTime() + "]");
					
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
					debug("CONSOLE_CLOSED [" + new SystemUtils().getTime() + "]");
					
					ConsoleClosedEvent event = new ConsoleClosedEvent();
					event.setWidth(e.getComponent().getWidth());
					event.setHeight(e.getComponent().getHeight());
					event.setX(e.getComponent().getX());
					event.setY(e.getComponent().getY());
					
					for(EventHandler handler : events.getHandlers(event.getClass())) {
						handler.execute(event);
					}
					
					close.close();
				}
				
				@Override
				public void windowDeactivated(WindowEvent e) {}
				
				@Override
				public void windowDeiconified(WindowEvent e) {}
				
				@Override
				public void windowIconified(WindowEvent e) {}
				
				@Override
				public void windowOpened(WindowEvent e) {
					debug("CONSOLE_OPENED [" + new SystemUtils().getTime() + "]");
					
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
			
			update();
		} 
		else {
			Console.println(ConsoleUser.Error, "Console is already created!");
		}
	}
	
	/**
	 * Use this message to debug something to the logging file. When logging is off, it won't log anything.
	 * @param text The text to debug
	 */
	public static void debug(String text) {
		if(text == null) {
			new NonNullableParameterError("String", "text").print();
			return;
		}
		
		if(settings == null) { //TODO: FIX THIS
			return;
		}
		
		if(settings.contains("log")) {
			if(settings.get("log").equals("true") && bw != null) {
				try {
					byte[] bytes = text.getBytes();
					
					for(int i = 0; i < bytes.length; i++) {
						bw.write(bytes[i]);
					}
					
					bw.write('\n');
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This is the PrintStream for the console.
	 * @see Console
	 */
	static class ConsolePrintStream extends PrintStream {

		private ConsolePrintStream(OutputStream out) {
			super(out);
		}
	}
	
	/**
	 * This is the OutputStream for the console.
	 * @see Console
	 */
	static class ConsoleOutputStream extends OutputStream {
		
		private String lineText = "";
		private String fullLine = "";
		
		private List<Integer> brakes = new ArrayList<Integer>();
		
		private ConsoleOutputStream() {
			at = new SimpleAttributeSet();
		}
		
		@Override
		public void write(int b) throws IOException {
			String text = new String(new byte[]{(byte)b});
			
			int l = t.getGraphics().getFontMetrics().stringWidth(lineText + text);
			
			if((l > t.getWidth()) && !text.equals("\n")) {
				brakes.add(d.getLength());
				
				try {
					d.insertString(d.getLength(), "\n", at);
				}
				catch (BadLocationException e) {}
				
				lineText = "";
			}
			
			if(text.equals("\n")) {
				brakes.add(d.getLength());
				
				try {
					d.insertString(d.getLength(), "\n", at);
				}
				catch (BadLocationException e) {}
				
				lineText = "";
				fullLine = "";
				return;
			}
			
			lineText += text;
			fullLine += text;
			
			try {
				d.insertString(d.getLength(), text, at);
			}
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
	
	/**
	 * This is the InputStream for the console.
	 * @see Console
	 */
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
				String text;
				try {
					text = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
					text = text.replace("\n", "");
					
					Console.write(text);
					presses += text.length();
					cos.fullLine += text;
				} 
				catch (HeadlessException | UnsupportedFlavorException | IOException e1) {
					Console.println(ConsoleUser.Error, "Unknown Error: " + e1.getMessage());
				}
				
				return;
			}
			
			if((byte)e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
				if(presses > 0) {
					try {
						d.remove(d.getLength()-1, 1);
					}
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
						
						try {
							d.remove(d.getLength()-1, 1);
						}
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
					} 
			        catch(Error | Exception e) {
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
	
	/**
	 * Print text to the console and end it with a new line.
	 * @param user The user that prints the line
	 * @param text The text to print
	 */
	public static void println(String user, String text) {
		if(text == null) {
			new NonNullableParameterError("String", "text").print();
			return;
		}
		if(user == null) {
			new NonNullableParameterError("String", "user").print();
			return;
		}
		
		String time = new SystemUtils().getTime();
		
		if(settings == null) {
			cps.println("[" + user + " " + time + "]: " + text);
		}
		else if(!settings.contains("time")) {
			cps.println("[" + user + " " + time + "]: " + text);
		}
		else if(settings.get("time").equals("true")) {
			cps.println("[" + user + " " + time + "]: " + text);
		}
		else if(settings.get("time").equals("false")) {
			cps.println("[" + user + "]: " + text);
		}
		
		debug("PRINTLN [" + time + "][" + user + "][" + text + "]");
	}
	
	/**
	 * Print text to the console and end it with a new line.
	 * @param text The text to print
	 */
	public static void println(String text) {
		println(ConsoleUser.System, text);
	}
	
	/**
	 * Write text to the console. Writing it means it won't add a prefix.
	 * @param text The text to write
	 */
	public static void write(String text) {
		if(text == null) {
			new NonNullableParameterError("String", "text").print();
			return;
		}
		
		cps.print(text);
		
		if(!text.equals("\n")) {
			debug("WRITE [" + new SystemUtils().getTime() + "][" + text + "]");
		}
	}
	
	/**
	 * Write text to the console and end it with a new line. Writing it means it won't add a prefix.
	 * @param text The text to write
	 */
	public static void writeln(String text) {
		if(text == null) {
			new NonNullableParameterError("String", "text").print();
			return;
		}
		
		cps.println(text);
		
		debug("WRITELN [" + new SystemUtils().getTime() + "][" + text + "]");
	}
	
	/**
	 * Read the keypresses until the user types and enter. And start it with the prefix.
	 * @return The text the user has typed
	 */
	public static String readln() {
		if(settings == null) {
			cps.print("[YOU " + new SystemUtils().getTime() + "]: ");
			return cis.waitUntilDone();
		}
		else if(!settings.contains("time")) {
			cps.print("[YOU " + new SystemUtils().getTime() + "]: ");
			return cis.waitUntilDone();
		}
		else if(settings.get("time").equals("true")) {
			cps.print("[YOU " + new SystemUtils().getTime() + "]: ");
			return cis.waitUntilDone();
		}
		else if(settings.get("time").equals("false")) {
			cps.print("[YOU]: ");
			return cis.waitUntilDone();
		}
		
		return "";
	}
	
	/**
	 * Read the keypresses until the user types and enter.
	 * @return The text the user has typed
	 */
	public static String read() {
		return cis.waitUntilDone();
	}
	
	/**
	 * Clears the console and executes all of the ConsoleClearedEvents.
	 */
	public static void clear() {
		debug("CLEAR [" + new SystemUtils().getTime() + "]");
		
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
	
	/**
	 * Updates the console with the newest settings.
	 */
	public static void update() {
		if(settings == null) {
			return;
		}
		
		if(settings.contains("foreground")) {
			Color fg = new ObjectUtils().StringToColor(settings.get("foreground"));
			
			if(t.getForeground() != fg) {
				debug("UPDATE [" + new SystemUtils().getTime() + "][FOREGROUND][" + settings.get("foreground") + "]");
				
				t.setForeground(fg);
			}
		}
		if(settings.contains("background")) {
			Color bg = new ObjectUtils().StringToColor(settings.get("background"));
			
			if(t.getBackground() != bg) {
				debug("UPDATE [" + new SystemUtils().getTime() + "][BACKGROUND][" + settings.get("background") + "]");
				
				t.setBackground(bg);
			}
		}
		if(settings.contains("default_title")) {
			String dt = settings.get("default_title");
			dt = dt.replace("_", " ");
			
			if(frame.getTitle() != dt) {
				debug("UPDATE [" + new SystemUtils().getTime() + "][DEFAULT_TITLE][" + settings.get("default_title") + "]");
				
				frame.setTitle(dt);
			}
		}
		if(settings.contains("default_width")) {
			int dw = new ObjectUtils().StringToInteger(settings.get("default_width"));
			
			if(frame.getWidth() != dw) {
				debug("UPDATE [" + new SystemUtils().getTime() + "][DEFAULT_WIDTH][" + settings.get("default_width") + "]");
				
				frame.setSize(dw, frame.getHeight());
			}
		}
		if(settings.contains("default_height")) {
			int dh = new ObjectUtils().StringToInteger(settings.get("default_height"));
			
			if(frame.getHeight() != dh) {
				debug("UPDATE [" + new SystemUtils().getTime() + "][DEFAULT_HEIGHT][" + settings.get("default_height") + "]");
				
				frame.setSize(frame.getWidth(), dh);
			}
		}
	}
	
	/**
	 * Closes the console.
	 * @throws IOException The debugging writer may throw an error while closing
	 */
	public static void close() throws IOException {
		debug("");
		debug("-----!JCIO!-----");
		
		bw.close();
	}
	
	/**
	 * Returns the KeyboardInput class that is used by the console.
	 * @return The keyboardinput class
	 */
	public static KeyboardInput getKeyboardInput() {
		return ki;
	}
	
	/**
	 * Returns the MouseInput class that is used by the console.
	 * @return The mouseinput class
	 */
	public static MouseInput getMouseInput() {
		return mi;
	}
	
	/**
	 * Sets the settingsmanager for the console. Please don't use it.
	 * @param settings The settingsmanager
	 */
	public static void setSettingsManager(SettingsManager settings) {
		Console.settings = settings;
	}
	
	/**
	 * Sets the eventmanager for the console. Please don't use it.
	 * @param events The eventmanager
	 */
	public static void setEventManager(EventManager events) {
		Console.events = events;
	}
	
	/**
	 * Sets the closemanager for the console. Please don't use it.
	 * @param close The closemanager
	 */
	public static void setCloseManager(CloseManager close) {
		Console.close = close;
	}
	
}
