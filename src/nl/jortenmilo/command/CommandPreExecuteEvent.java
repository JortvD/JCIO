package nl.jortenmilo.command;

public class CommandPreExecuteEvent extends CommandEvent {
	
	public interface CommandPreExecuteEventListener extends CommandEventListener {
		public void onCommandPreExecute(CommandPreExecuteEvent e);
	}
	
}
