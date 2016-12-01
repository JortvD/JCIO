package nl.jortenmilo.command;

public interface CommandExecutor {
	
	public void execute(String command, Command cmd, String[] params);

}
