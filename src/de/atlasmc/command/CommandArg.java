package de.atlasmc.command;

import java.util.List;

import de.atlasmc.permission.Permission;

public interface CommandArg {
	
	/**
	 * Returns the name of this argument or null to accept any input (only once valid each layer)
	 * @return name or null
	 */
	public String getName();
	
	public String getHelpText();
	
	public List<CommandArg> getArguments();
	
	public void addArgument(CommandArg arg);
	
	public void removeArgument(CommandArg arg);
	
	public boolean hasArguments();
	
	public List<String> getTabComplete();
	
	public CommandExecutor getExecutor();
	
	public void setExecutor(CommandExecutor executor);
	
	public boolean hasExecutor();
	
	public Permission getPermission();
	
	public void setPermission(Permission permission);
	
	public boolean hasPermission();

}
