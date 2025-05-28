package de.atlasmc.command;

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
	private final List<CommandArg> parsedArgs;
	private final CommandArg lastArg;
	private final Map<String, ?> arguments;
	
	public CommandContext(CommandSender sender, String rawCommand, Command command, List<CommandArg> parsedArgs, Map<String, Object> arguments, CommandArg lastArg) {
		if (sender == null)
			throw new IllegalArgumentException("Sender can not be null!");
		if (rawCommand == null)
			throw new IllegalArgumentException("Raw command can not be null!");
		if (command == null)
			throw new IllegalArgumentException("Command can not be null!");
		if (lastArg == null)
			throw new IllegalArgumentException("Last arg can not be null!");
		this.sender = sender;
		this.rawCommand = rawCommand;
		this.command = command;
		this.parsedArgs = parsedArgs != null ? List.copyOf(parsedArgs) : List.of();
		this.arguments = arguments != null ? Map.copyOf(arguments) : Map.of();
		this.lastArg = lastArg;
	}
	
	/**
	 * Returns the sender
	 * @return sender
	 */
	public CommandSender getSender() {
		return sender;
	}
	
	/**
	 * Returns the command string
	 * @return command
	 */
	public String getRawCommand() {
		return rawCommand;
	}
	
	/**
	 * Returns the command
	 * @return command
	 */
	public Command getCommand() {
		return command;
	}
	
	/**
	 * Returns the last parsed argument of the command
	 * @return argument
	 */
	public CommandArg getLastArg() {
		return lastArg;
	}
	
	/**
	 * Returns a map of all parsed variable arguments
	 * @return arguments
	 */
	public Map<String, ?> getArguments() {
		return arguments;
	}
	
	/**
	 * Returns a list of all parsed arguments in order
	 * @return arguments
	 */
	public List<CommandArg> getParsedArgs() {
		return parsedArgs;
	}
	
	/**
	 * Returns a parsed variable argument with the given key of the given type.
	 * @param <T>
	 * @param name
	 * @param type
	 * @return argument
	 * @see #getArgument(String, Class, boolean)
	 */
	public <T> T getArgument(String name, Class<T> type) {
		return getArgument(name, type, true);
	}
	
	/**
	 * Returns a parsed variable argument with the given key of the given type.
	 * @param <T>
	 * @param name of the argument
	 * @param type of the argument
	 * @param required if this argument is required
	 * @return argument
	 * @throws IllegalArgumentException if the present argument is not a instance of  the given type or is missing and required is true
	 */
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

	/**
	 * Tries to execute the {@link CommandExecutor} of {@link #getLastArg()}.
	 * Returns whether or not the execution was successful.
	 * Always returns false if the executor is null.
	 * @return true if successful 
	 */
	public boolean execute() {
		CommandSourceValidator validator = lastArg.getSourceValidator();
		if (validator != null && !validator.isValid(sender))
			return false; // TODO InvalidCommandSourceException
		String permission = lastArg.getPermission();
		if (permission != null && !sender.hasPermission(permission))
			return false; // TODO NoPermissionException
		CommandExecutor exe = lastArg.getExecutor();
		if (exe != null)
			return exe.execute(this);
		return false;
	}

}
