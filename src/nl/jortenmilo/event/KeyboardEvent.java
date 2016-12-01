package nl.jortenmilo.event;

public class KeyboardEvent extends Event {
	
	private int keyCode;
	private char keyChar;
	private String keyText;
	private String modifiersText;
	
	public int getKeyCode() {
		return keyCode;
	}
	
	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public char getKeyChar() {
		return keyChar;
	}

	public void setKeyChar(char keyChar) {
		this.keyChar = keyChar;
	}

	public String getKeyText() {
		return keyText;
	}

	public void setKeyText(String keyText) {
		this.keyText = keyText;
	}

	public String getModifiersText() {
		return modifiersText;
	}

	public void setModifiersText(String modifiersText) {
		this.modifiersText = modifiersText;
	}
	
}
