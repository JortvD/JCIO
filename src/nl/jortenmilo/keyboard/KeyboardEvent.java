package nl.jortenmilo.keyboard;

import nl.jortenmilo.event.Event;

/**
 * This is the general KeyboardEvent. It contains all the general information for an event.
 * @see KeyboardManager
 */
public abstract class KeyboardEvent extends Event {
	
	private int keyCode;
	private char keyChar;
	private String keyText;
	private String modifiersText;
	
	/**
	 * Returns the key code of the key that was used.
	 * @return The key code
	 */
	public int getKeyCode() {
		return keyCode;
	}
	
	protected void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}
	
	/**
	 * Returns the key char of the key that was used.
	 * @return The key char
	 */
	public char getKeyChar() {
		return keyChar;
	}

	protected void setKeyChar(char keyChar) {
		this.keyChar = keyChar;
	}
	
	/**
	 * Returns the key text of the key that was used.
	 * @return The key text
	 */
	public String getKeyText() {
		return keyText;
	}

	protected void setKeyText(String keyText) {
		this.keyText = keyText;
	}
	
	/**
	 * Returns the text for the modifiers.
	 * @return The modifiers text
	 */
	protected String getModifiersText() {
		return modifiersText;
	}

	public void setModifiersText(String modifiersText) {
		this.modifiersText = modifiersText;
	}
	
}
