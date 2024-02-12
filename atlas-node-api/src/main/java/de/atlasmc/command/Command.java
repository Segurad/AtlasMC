package de.atlasmc.command;

import java.util.Collection;

public interface Command extends CommandArg {
	
	Collection<String> getAliases();
	
	void addAlias(String alias);
	
	void removeAlias(String alias);
	
	boolean hasAliases();

}