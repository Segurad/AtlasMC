package de.atlasmc.command;

public class CommandSyntaxException extends CommandException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int cursor;
	
	public CommandSyntaxException(String rawCommand, int cursor, String msg) {
		super(rawCommand, msg);
		this.cursor = cursor;
	}
	
	public CommandSyntaxException(String rawCommand, int cursor, Throwable cause) {
		super(rawCommand, cause);
		this.cursor = cursor;
	}
	
	public CommandSyntaxException(String rawCommand, int cursor, String msg, Throwable cause) {
		super(rawCommand, msg, cause);
		this.cursor = cursor;
	}
	
	public int getCursor() {
		return cursor;
	}

}
