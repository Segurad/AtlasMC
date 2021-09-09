package de.atlasmc.command;

import java.util.List;

public interface Command extends CommandArg {
	
	public List<String> getAliases();
	
	public void addAlias(String alias);
	
	public void removeAlias(String alias);
	
	public boolean hasAliases();

}