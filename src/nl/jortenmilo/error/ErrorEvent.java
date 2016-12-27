package nl.jortenmilo.error;

public class ErrorEvent {
	
	private Error error;

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
	
	public interface ErrorEventListener {
		public void onErrorThrown(ErrorThrownEvent e);
	}

}
