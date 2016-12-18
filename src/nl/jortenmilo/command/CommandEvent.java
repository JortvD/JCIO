package nl.jortenmilo.command;

public class CommandEvent {
	
	private Command command;
	private String[] arguments;
	
	public Command getCommand() {
		return command;
	}
	
	protected void setCommand(Command command) {
		this.command = command;
	}

	public String[] getArguments() {
		return arguments;
	}

	protected void setArguments(String[] arguments) {
		this.arguments = arguments;
	}
	
	public interface CommandEventListener {
		public void onCommandPreExecute(CommandPreExecuteEvent e);
		public void onCommandPostExecute(CommandPostExecuteEvent e);
	}

	
}
