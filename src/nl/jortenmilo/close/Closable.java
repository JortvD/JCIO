package nl.jortenmilo.close;

import nl.jortenmilo.error.NullableParameterError;

public abstract class Closable {
	
	private ClosablePriority priority = ClosablePriority.MEDIUM;
	
	public abstract void close();

	public ClosablePriority getPriority() {
		return priority;
	}

	public void setPriority(ClosablePriority priority) {
		if(priority == null) {
			new NullableParameterError("ClosablePriority", "priority").print();
			return;
		}
		
		this.priority = priority;
	}
	
}
