package nl.jortenmilo.storage;

import nl.jortenmilo.event.Event;

public abstract class StoragableEvent extends Event {
	
	private Storagable storagable;

	public Storagable getStoragable() {
		return storagable;
	}

	protected void setStoragable(Storagable storagable) {
		this.storagable = storagable;
	}

}
