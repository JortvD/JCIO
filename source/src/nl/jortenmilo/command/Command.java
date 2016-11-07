package nl.jortenmilo.command;

import nl.jortenmilo.error.InvalidParameterError;

public class Command {
	
	private String command;
	private String desc = "";
	private CommandExecutor ce;
	
	public Command() {}
	
	public Command(String command, CommandExecutor ce) {
		this.command = command;
		this.ce = ce;
	}
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		if(command==null) {
			new InvalidParameterError(command).print();
		}
		if(command.equals("")) {
			new InvalidParameterError(command).print();
		}
		this.command = command;
	}
	
	public CommandExecutor getCommandExecutor() {
		return ce;
	}
	
	public void setCommandExecutor(CommandExecutor ce) {
		this.ce = ce;
	}

	public String getDescription() {
		return desc;
	}

	public void setDescription(String desc) {
		this.desc = desc;
	}
	
}
