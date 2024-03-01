package de.atlasmc.command;

public class CommandException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String rawCommand;
	
	public CommandException(String rawCommand, String msg) {
		super(msg);
		this.rawCommand = rawCommand;
	}
	
	public CommandException(String rawCommand, Throwable cause) {
		super(cause);
		this.rawCommand = rawCommand;
	}
	
	public CommandException(String rawCommand, String msg, Throwable cause) {
		super(msg, cause);
		this.rawCommand = rawCommand;
	}
	
	public String getRawCommand() {
		return rawCommand;
	}
	
}
