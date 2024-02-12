package de.atlasmc.command;

public interface CommandExecutor {
	
	/**
	 * 
	 * @param sender the  CommandSender 
	 * @param command the Command
	 * @param label the alias of this Command that has been used 
	 * @param args input arguments
	 * @param depth the depth of this executor to the command root 0 if it is the root
	 * @return true if the Command has been executed successfully
	 */
	boolean execute(CommandSender sender, Command command, String label, String[] args, int depth);

}
