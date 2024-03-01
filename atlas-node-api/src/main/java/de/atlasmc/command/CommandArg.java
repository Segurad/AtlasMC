package de.atlasmc.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public class CommandArg {
	
	public static final Predicate<CommandSender> 
	PLAYERS_ONLY = (sender) -> {
		return sender instanceof AtlasPlayer;
	},
	CONSOLE_ONLY = (sender) -> {
		return sender instanceof ConsoleCommandSender;
	};
	
	private final String name;
	private CommandExecutor executor;
	private String permission;
	private Predicate<CommandSender> senderValidator; 
	private List<CommandArg> args;
	private Map<String, LiteralCommandArg> literalArgs;
	private Map<String, VarCommandArg> varArgs;
 	
	public CommandArg(@NotNull String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		this.name = name;
	}
	
	/**
	 * Returns the name of this argument
	 * @return name
	 */
	@NotNull
	public String getName() {
		return name;
	}

	@Nullable
	public CommandExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(@Nullable CommandExecutor executor) {
		this.executor = executor;
	}

	public boolean hasExecutor() {
		return executor != null;
	}

	@Nullable
	public String getPermission() {
		return permission;
	}

	public void setPermission(@Nullable String permission) {
		this.permission = permission;
	}

	public boolean hasPermission() {
		return permission != null;
	}

	@NotNull
	public Collection<CommandArg> getArguments() {
		if (args == null)
			return List.of();
		return args;
	}
	
	public boolean hasArguments() {
		return args != null && !args.isEmpty();
	}

	@NotNull
	public CommandArg setArg(CommandArg arg) {
		if (arg == null)
			throw new IllegalArgumentException("Argument can not be null!");
		if (arg instanceof LiteralCommandArg literal) {
			if (literalArgs == null)
				literalArgs = new HashMap<>();
			literalArgs.put(literal.getName(), literal);
		} else if (arg instanceof VarCommandArg varArg) {
			if (varArgs == null)
				varArgs = new HashMap<>();
			varArgs.put(varArg.getName(), varArg);
		} else {
			throw new IllegalArgumentException("Argument must be a instance of VarCommandArg or LiteralCommandArg: " + arg.getClass().getName());
		}
		if (args == null) 
			args = new ArrayList<>();
		args.add(arg);
		return this;
	}
	
	@NotNull
	public Collection<LiteralCommandArg> getLiteralArgs() {
		if (literalArgs == null)
			return List.of();
		return literalArgs.values();
	}
	
	public boolean hasLiteralArgs() {
		return literalArgs != null && !varArgs.isEmpty();
	}
	
	@Nullable
	public LiteralCommandArg getLiteralArg(@NotNull String name) {
		if (args == null)
			return null;
		return literalArgs.get(name);
	}
	
	@NotNull
	public Collection<VarCommandArg> getVarArgs() {
		if (varArgs == null)
			return List.of();
		return varArgs.values();
	}
	
	@Nullable
	public VarCommandArg getVarArg(@NotNull String name) {
		if (args == null)
			return null;
		return varArgs.get(name);
	}
	
	public boolean hasVarArgs() {
		return varArgs != null && !varArgs.isEmpty();
	}
	
	@Nullable
	public Predicate<CommandSender> getSenderValidator() {
		return senderValidator;
	}

	public void setSenderValidator(@Nullable Predicate<CommandSender> senderValidator) {
		this.senderValidator = senderValidator;
	}
	
	public boolean canUse(@NotNull CommandSender sender) {
		if (senderValidator != null && !senderValidator.test(sender))
			return false;
		if (permission != null && !sender.hasPermission(permission))
			return false;
		return true;
	}
	
}
