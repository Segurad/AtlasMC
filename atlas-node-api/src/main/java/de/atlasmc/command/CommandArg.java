package de.atlasmc.command;

import java.util.List;

import de.atlasmc.permission.Permission;

public interface CommandArg {
	
	/**
	 * Returns the name of this argument or null to accept any input (only once valid each layer)
	 * @return name or null
	 */
	String getName();
	
	String getHelpText();
	
	List<CommandArg> getArguments();
	
	void addArgument(CommandArg arg);
	
	void removeArgument(CommandArg arg);
	
	boolean hasArguments();
	
	List<String> getTabComplete();
	
	CommandExecutor getExecutor();
	
	void setExecutor(CommandExecutor executor);
	
	boolean hasExecutor();
	
	Permission getPermission();
	
	void setPermission(Permission permission);
	
	boolean hasPermission();

}
