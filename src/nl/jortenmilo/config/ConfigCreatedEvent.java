package nl.jortenmilo.config;

/**
 * This event is executed when a configuration is created.
 */
public class ConfigCreatedEvent extends ConfigEvent {

	@Override
	public String getName() {
		return "ConfigCreatedEvent";
	}

}
