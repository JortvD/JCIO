package nl.jortenmilo.command;

public class CommandPostExecuteEvent extends CommandEvent {
	
	public interface CommandPostExecuteEventListener extends CommandEventListener {
		public void onCommandPostExecute(CommandPostExecuteEvent e);
	}
	
}
