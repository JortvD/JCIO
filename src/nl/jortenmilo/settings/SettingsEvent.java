package nl.jortenmilo.settings;

public class SettingsEvent {
	
	public interface SettingsEventListener {
		public void onSettingsCreated(SettingsCreatedEvent event);
		public void onSettingsChanged(SettingsChangedEvent event);
		public void onSettingsReset(SettingsResetEvent event);
		public void onSettingsRemoved(SettingsRemovedEvent event);
		public void onSettingsSaved(SettingsSavedEvent event);
		public void onSettingsLoaded(SettingsLoadedEvent event);
	}
	
}
