package nl.jortenmilo.error;

/**
 * This error is thrown when a settings is created that already exists.
 * @see Error
 */
public class ExistingSettingError extends Error {
	
	private String value;
	
	public ExistingSettingError(String value) {
		this.value = value;
	}
	
	@Override
	public void print() {
		this.printError("ExistingSettingError", "The setting '" + value + "' already exists!");
		this.printStackTrace();
		this.printHelp();
		this.event();
	}
	
}
