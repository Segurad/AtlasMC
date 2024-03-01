package de.atlasmc.command;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CommandContext {
	
	private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER;
	
	static {
		PRIMITIVE_TO_WRAPPER = Map.of(
				boolean.class, Boolean.class,
				byte.class, Byte.class, 
				short.class, Short.class, 
				int.class, Integer.class, 
				long.class, Long.class,
				float.class, Float.class, 
				double.class, Double.class);
	}
	
	private final CommandSender sender;
	private final String rawCommand;
	private final Command command;
	private final Collection<CommandArg> parsedArgs;
	private final CommandArg lastArg;
	private final Map<String, ?> arguments;
	
	public CommandContext(CommandSender sender, String rawCommand, Command command, List<CommandArg> parsedArgs, Map<String, Object> arguments, CommandArg lastArg) {
		this.sender = sender;
		this.rawCommand = rawCommand;
		this.command = command;
		this.parsedArgs = parsedArgs != null ? List.copyOf(parsedArgs) : List.of();
		this.arguments = arguments != null ? Map.copyOf(arguments) : Map.of();
		this.lastArg = lastArg;
	}
	
	public CommandSender getSender() {
		return sender;
	}
	
	public String getRawCommand() {
		return rawCommand;
	}
	
	public Command getCommand() {
		return command;
	}
	
	public CommandArg getLastArg() {
		return lastArg;
	}
	
	public Map<String, ?> getArguments() {
		return arguments;
	}
	
	public Collection<CommandArg> getParsedArgs() {
		return parsedArgs;
	}
	
	public <T> T getArgument(String name, Class<T> type) {
		return getArgument(name, type, true);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getArgument(String name, Class<T> type, boolean required) {
		final Object arg = arguments.get(name);
		if (arg == null) {
			if (required) {
				throw new IllegalArgumentException("No argument found with name: " + name);
			}
			return null;
		}
		if (!PRIMITIVE_TO_WRAPPER.getOrDefault(type, type).isInstance(arg)) {
			throw new IllegalArgumentException("Argument " + name + " of type " + arg.getClass().getName() + " is not assignable from " + type.getName());
		}
		return (T) arg;
	}

}
