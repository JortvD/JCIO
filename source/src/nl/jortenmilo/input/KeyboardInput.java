package nl.jortenmilo.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;

public class KeyboardInput implements KeyListener {
	
	private static HashMap<Integer, Boolean> pressed = new HashMap<Integer, Boolean>();
	private static List<Integer> wait = new ArrayList<Integer>();
	private static CountDownLatch latch;
	private static int typed = -1;
	
	@Override
	public void keyPressed(KeyEvent e) {
		pressed.put(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressed.put(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(wait.size()==1) {
			if(wait.get(0) == 0) {
				typed = e.getKeyChar();
				latch.countDown();
				wait.clear();
				latch = null;
			}
		}
		
		for(int i = 0; i < wait.size(); i++) {
			int n = wait.get(i);
			
			if(n == e.getKeyCode()) {
				latch.countDown();
				wait.clear();
				latch = null;
			}
		}
	}
	
	public static int waitUntilTyped() {
		if(latch != null) {
			Console.println(ConsoleUser.Error, "There is already a latch waiting for a key to be pressed!");
			return -1;
		}
		
		latch = new CountDownLatch(1);
		
		wait.add(0);
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		int typed2 = typed;
		typed = -1;
		return typed2;
	}
	
	public static void waitUntilTyped(int key) {
		if(latch != null) {
			Console.println(ConsoleUser.Error, "There is already a latch waiting for a key to be pressed!");
			return;
		}
		
		latch = new CountDownLatch(1);
		
		wait.add(key);
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean isPressed(int key) {
		if(pressed.containsKey(key)) {
			return pressed.get(key);
		}
		
		return false;
	}
	
	public static int waitUntilTyped(int[] keys) {
		int t = waitUntilTyped();
		boolean a = false;
		
		for(int i = 0; i < keys.length; i++) {
			if(keys[i] == t) {
				a = true;
			}
		}
		
		if(!a) {
			t = waitUntilTyped(keys);
		}
		
		
		
		return t;
	}

}
