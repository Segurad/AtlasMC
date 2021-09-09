package de.atlascore.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.atlasmc.command.Command;
import de.atlasmc.command.CommandExecutor;

public class CoreCommand extends CoreCommandArg implements Command {

	private List<String> aliases;
	
	public CoreCommand(String name, String help, CommandExecutor executor, String... aliases) {
		super(name, help, executor);
		if (aliases != null) {
			this.aliases = Arrays.asList(aliases);
		}
	}

	@Override
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public void addAlias(String alias) {
		if (aliases == null) aliases = new ArrayList<>(1);
		aliases.add(alias);
	}

	@Override
	public void removeAlias(String alias) {
		if (aliases == null) return;
		aliases.add(alias);
	}

	@Override
	public boolean hasAliases() {
		return aliases != null && !aliases.isEmpty();
	}

}
