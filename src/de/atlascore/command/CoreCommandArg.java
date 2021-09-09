package de.atlascore.command;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.command.CommandArg;
import de.atlasmc.command.CommandExecutor;

public class CoreCommandArg implements CommandArg {

	private final String name, help;
	private CommandExecutor executor;
	private List<CommandArg> args;
	
	public CoreCommandArg(String name, String help, CommandExecutor executor) {
		this.name = name;
		this.help = help;
		this.executor = executor;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getHelpText() {
		return help;
	}

	@Override
	public List<CommandArg> getArguments() {
		return args;
	}

	@Override
	public void addArgument(CommandArg arg) {
		if (args == null) args = new ArrayList<CommandArg>(1);
		args.add(arg);
	}

	@Override
	public void removeArgument(CommandArg arg) {
		if (args == null) return;
		args.remove(arg);
	}
	
	@Override
	public boolean hasArguments() {
		return args != null && !args.isEmpty();
	}

	@Override
	public List<String> getTabComplete() {
		if (args == null) return List.of();
		ArrayList<String> tabs = new ArrayList<>();
		for (CommandArg arg : args) {
			tabs.add(arg.getName());
		}
		return tabs;
	}

	@Override
	public CommandExecutor getExecutor() {
		return executor;
	}

	@Override
	public void setExecutor(CommandExecutor executor) {
		this.executor = executor;
	}

	@Override
	public boolean hasExecutor() {
		return executor != null;
	}
	
}
