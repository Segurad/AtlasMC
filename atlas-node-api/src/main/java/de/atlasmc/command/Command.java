package de.atlasmc.command;

public class Command extends LiteralCommandArg {
	
	private String commandDescription;
	
	public Command(String name, String... aliases) {
		super(name);
	}
	
	public String getCommandDescription() {
		return commandDescription;
	}
	
	public void setCommandDescription(String commandDescription) {
		this.commandDescription = commandDescription;
	}

}