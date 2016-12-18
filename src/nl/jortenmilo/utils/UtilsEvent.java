package nl.jortenmilo.utils;

public class UtilsEvent {
	
	private String name = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public class UtilsEventListener {
		public void onUtilsCreated(UtilsCreatedEvent event) {}
		public void onUtilsCloned(UtilsClonedEvent event) {}
	}
	
}
