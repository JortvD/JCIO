package nl.jortenmilo.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;
import nl.jortenmilo.error.InvalidParameterError;
import nl.jortenmilo.keyboard.KeyboardEvent.KeyboardEventListener;
import nl.jortenmilo.plugin.Plugin;

public class KeyboardInput implements KeyListener {
	
	private List<KeyboardEventListener> listeners = new ArrayList<KeyboardEventListener>();
	private HashMap<Plugin, List<KeyboardEventListener>> plisteners = new HashMap<Plugin, List<KeyboardEventListener>>();
	private HashMap<Integer, Boolean> pressed = new HashMap<Integer, Boolean>();
	private List<Integer> wait = new ArrayList<Integer>();
	private CountDownLatch latch;
	private int typed = -1;
	
	@Override
	public void keyPressed(KeyEvent e) {
		KeyboardPressedEvent event = new KeyboardPressedEvent();
		event.setKeyChar(e.getKeyChar());
		event.setKeyCode(e.getKeyCode());
		event.setKeyText(KeyEvent.getKeyText(e.getKeyCode()));
		event.setModifiersText(KeyEvent.getKeyModifiersText(e.getModifiers()));
		
		for(KeyboardEventListener kel : listeners) {
			try {
				kel.onKeyboardPressed(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
		}
		
		pressed.put(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		KeyboardReleasedEvent event = new KeyboardReleasedEvent();
		event.setKeyChar(e.getKeyChar());
		event.setKeyCode(e.getKeyCode());
		event.setKeyText(KeyEvent.getKeyText(e.getKeyCode()));
		event.setModifiersText(KeyEvent.getKeyModifiersText(e.getModifiers()));
		
		for(KeyboardEventListener kel : listeners) {
			try {
				kel.onKeyboardReleased(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
		}
		
		pressed.put(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		KeyboardTypedEvent event = new KeyboardTypedEvent();
		event.setKeyChar(e.getKeyChar());
		event.setKeyCode(e.getKeyCode());
		event.setKeyText(KeyEvent.getKeyText(e.getKeyCode()));
		event.setModifiersText(KeyEvent.getKeyModifiersText(e.getModifiers()));
		
		for(KeyboardEventListener kel : listeners) {
			try {
				kel.onKeyboardTyped(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
		}
		
		if(wait.size()==1) {
			if(wait.get(0) == 0) {
				typed = e.getKeyChar();
				countDown();
				wait.clear();
				return;
			}
		}
		
		for(int i = 0; i < wait.size(); i++) {
			int n = wait.get(i);
			
			if(n == e.getKeyCode()) {
				typed = e.getKeyChar();
				countDown();
				wait.clear();
			}
		}
	}
	
	private void countDown() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				latch.countDown();
			}
		});
		t.start();
	}
	
	protected void addListener(KeyboardEventListener listener, Plugin plugin) {
		if(plugin == null) {
			//Throw an error when the plugin is null.
			new InvalidParameterError(plugin + "").print();
			return;
		}
		
		listeners.add(listener);
		
		List<KeyboardEventListener> l = plisteners.get(plugin);
		l.add(listener);
		plisteners.put(plugin, l);
	}
	
	protected List<KeyboardEventListener> getListeners() {
		return listeners;
	}
	
	protected void removeListener(KeyboardEventListener listener) {
		listeners.remove(listener);
		
		Plugin plugin = getPlugin(listener);
		List<KeyboardEventListener> l = plisteners.get(plugin);
		l.remove(listener);
		plisteners.put(plugin, l);
	}
	
	protected void removeListeners(Plugin plugin) {
		for(KeyboardEventListener listener : plisteners.get(plugin)) {
			listeners.remove(listener);
		}
		plisteners.remove(plugin);
	}
	
	private Plugin getPlugin(KeyboardEventListener listener) {
		for(Plugin plugin : plisteners.keySet()) {
			for(KeyboardEventListener c : plisteners.get(plugin)) {
				if(c==listener) return plugin;
			}
		}
		return null;
	}
	
	protected int waitUntilTyped() {
		if(latch != null) {
			Console.println(ConsoleUser.Error, "There is already a latch waiting for a key to be pressed!");
			return -1;
		}
		
		latch = new CountDownLatch(1);
		
		wait.add(0);
		
		try {
			latch.await();
		} catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
		}
		int typed2 = typed;
		typed = -1;
		return typed2;
	}
	
	protected void waitUntilTyped(int key) {
		if(latch != null) {
			Console.println(ConsoleUser.Error, "There is already a latch waiting for a key to be pressed!");
			return;
		}
		
		latch = new CountDownLatch(1);
		
		wait.add(key);
		
		try {
			latch.await();
		} catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
		}
	}
	
	protected boolean isPressed(int key) {
		if(pressed.containsKey(key)) {
			return pressed.get(key);
		}
		
		return false;
	}
	
	protected int waitUntilTyped(int[] keys) {
		for(int i = 0; i < keys.length; i++) {
			wait.add(keys[i]);
		}
		
		latch = new CountDownLatch(1);
		
		try {
			latch.await();
		} catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.getMessage()).print();
		}
		
		int typed2 = typed;
		typed = -1;
		return typed2;
	}

}
