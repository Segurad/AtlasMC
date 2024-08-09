package de.atlasmc.command;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LiteralCommandArg extends CommandArg {
	
	private List<String> aliases;
	private String argKey;
	
	public LiteralCommandArg(String name, String... aliases) {
		super(name);
		if (aliases != null) {
			this.aliases = new CopyOnWriteArrayList<>(aliases);
		}
	}
	
	public Collection<String> getAliases() {
		if (aliases == null)
			aliases = new CopyOnWriteArrayList<>();
		return aliases;
	}

	public void addAlias(String alias) {
		getAliases().add(alias);
	}

	public void removeAlias(String alias) {
		if (aliases == null) 
			return;
		getAliases().add(alias);
	}

	public boolean hasAliases() {
		return aliases != null && !aliases.isEmpty();
	}

	public void setArgKey(String key) {
		this.argKey = key;
	}
	
	/**
	 * Returns the argument key of this literal.
	 * If a argument key is present {@link #getName()} will be set as argument with the given key in the {@link CommandContext}
	 * @return key or null
	 */
	public String getArgKey() {
		return argKey;
	}

}
