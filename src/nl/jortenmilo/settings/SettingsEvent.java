package nl.jortenmilo.settings;

public class SettingsEvent {
	
	private String key;
	private String value;
	
	public String getKey() {
		return key;
	}
	
	protected void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	protected void setValue(String value) {
		this.value = value;
	}
	
	public class SettingsEventListener {
		public void onSettingsCreated(SettingsCreatedEvent event) {}
		public void onSettingsChanged(SettingsChangedEvent event) {}
		public void onSettingsReset(SettingsResetEvent event) {}
		public void onSettingsRemoved(SettingsRemovedEvent event) {}
	}
	
}
