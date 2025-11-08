package de.atlasmc.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.atlasmc.util.Builder;

public class CommandContextBuilder implements Builder<CommandContext> {

	private CommandSender sender;
	private String rawCommand;
	private Command command;
	private List<CommandArg> parsedArgs;
	private CommandArg lastArg;
	private Map<String, Object> arguments;
	
	public CommandSender getSender() {
		return sender;
	}
	
	public CommandContextBuilder setSender(CommandSender sender) {
		this.sender = sender;
		return this;
	}
	
	public List<CommandArg> getParsedArgs() {
		if (parsedArgs == null)
			parsedArgs = new ArrayList<>();
		return parsedArgs;
	}
	
	public CommandContextBuilder addParsedArg(CommandArg arg) {
		getParsedArgs().add(arg);
		return this;
	}
	
	public CommandContextBuilder setParsedArgs(List<CommandArg> parsedArgs) {
		this.parsedArgs = parsedArgs;
		return this;
	}
	
	public CommandArg getLastArg() {
		return lastArg;
	}
	
	public CommandContextBuilder setLastArg(CommandArg lastArg) {
		this.lastArg = lastArg;
		return this;
	}
	
	public Command getCommand() {
		return command;
	}
	
	public CommandContextBuilder setCommand(Command command) {
		this.command = command;
		setLastArg(command);
		return this;
	}
	
	public String getRawCommand() {
		return rawCommand;
	}
	
	public CommandContextBuilder setRawCommand(String rawCommand) {
		this.rawCommand = rawCommand;
		return this;
	}
	
	public CommandContextBuilder setArguments(Map<String, Object> arguments) {
		this.arguments = arguments;
		return this;
	}
	
	public Map<String, Object> getArguments() {
		if (arguments == null)
			arguments = new HashMap<>();
		return arguments;
	}
	
	public CommandContextBuilder addArgument(String key, Object value) {
		getArguments().put(key, value);
		return this;
	}
	
	/**
	 * Adds the given argument as last parsed argument
	 * @param argument
	 * @return
	 */
	public CommandContextBuilder updateArguments(CommandArg argument) {
		addParsedArg(argument);
		setLastArg(argument);
		return this;
	}
	
	@Override
	public CommandContext build() {
		return new CommandContext(sender, rawCommand, command, parsedArgs, arguments, lastArg);
	}

	public void clear() {
		sender = null;
		rawCommand = null;
		command = null;
		parsedArgs = null;
		arguments = null;
		lastArg = null;
	}

}
