package nl.jortenmilo.keyboard;

public class KeyboardEvent{
	
	private int keyCode;
	private char keyChar;
	private String keyText;
	private String modifiersText;
	
	public int getKeyCode() {
		return keyCode;
	}
	
	protected void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public char getKeyChar() {
		return keyChar;
	}

	protected void setKeyChar(char keyChar) {
		this.keyChar = keyChar;
	}

	public String getKeyText() {
		return keyText;
	}

	protected void setKeyText(String keyText) {
		this.keyText = keyText;
	}

	protected String getModifiersText() {
		return modifiersText;
	}

	public void setModifiersText(String modifiersText) {
		this.modifiersText = modifiersText;
	}
	
}
