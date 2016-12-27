package nl.jortenmilo.config;

public class ConfigEvent {
	
	private ConfigFile config;

	public ConfigFile getConfig() {
		return config;
	}

	protected void setConfig(ConfigFile config) {
		this.config = config;
	}
	
	public interface ConfigEventListener {
		public void onConfigCreated(ConfigCreatedEvent e);
		public void onConfigLoaded(ConfigLoadedEvent e);
		public void onConfigSaved(ConfigSavedEvent e);
	}
}
