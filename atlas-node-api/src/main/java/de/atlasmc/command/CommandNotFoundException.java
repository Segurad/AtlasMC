package de.atlasmc.command;

public class CommandNotFoundException extends CommandException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommandNotFoundException(String rawCommand) {
		super(rawCommand, "Command not found: " + rawCommand);
	}

}
