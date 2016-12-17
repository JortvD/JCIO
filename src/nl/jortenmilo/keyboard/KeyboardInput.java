package nl.jortenmilo.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;

public class KeyboardInput implements KeyListener {
	
	private List<KeyboardEventListener> kels = new ArrayList<KeyboardEventListener>();
	private HashMap<Integer, Boolean> pressed = new HashMap<Integer, Boolean>();
	private List<Integer> wait = new ArrayList<Integer>();
	private CountDownLatch latch;
	private int typed = -1;
	
	@Override
	public void keyPressed(KeyEvent e) {
		for(KeyboardEventListener kel : kels) {
			KeyboardPressedEvent event = new KeyboardPressedEvent();
			event.setKeyChar(e.getKeyChar());
			event.setKeyCode(e.getKeyCode());
			event.setKeyText(KeyEvent.getKeyText(e.getKeyCode()));
			event.setModifiersText(KeyEvent.getKeyModifiersText(e.getModifiers()));
			
			try {
				kel.onPressed(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
		}
		
		pressed.put(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		for(KeyboardEventListener kel : kels) {
			KeyboardReleasedEvent event = new KeyboardReleasedEvent();
			event.setKeyChar(e.getKeyChar());
			event.setKeyCode(e.getKeyCode());
			event.setKeyText(KeyEvent.getKeyText(e.getKeyCode()));
			event.setModifiersText(KeyEvent.getKeyModifiersText(e.getModifiers()));
			
			try {
				kel.onReleased(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
		}
		
		pressed.put(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		for(KeyboardEventListener kel : kels) {
			KeyboardTypedEvent event = new KeyboardTypedEvent();
			event.setKeyChar(e.getKeyChar());
			event.setKeyCode(e.getKeyCode());
			event.setKeyText(KeyEvent.getKeyText(e.getKeyCode()));
			event.setModifiersText(KeyEvent.getKeyModifiersText(e.getModifiers()));
			
			try {
				kel.onTyped(event);
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
	
	protected void addEventListener(KeyboardEventListener e) {
		kels.add(e);
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

	public List<KeyboardEventListener> getListeners() {
		return kels;
	}
	
	

}
