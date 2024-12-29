package de.atlasmc.command;

import static de.atlasmc.chat.component.ChatComponent.base;
import static de.atlasmc.chat.component.ChatComponent.chat;
import static de.atlasmc.chat.component.ChatComponent.space;
import static de.atlasmc.chat.component.ChatComponent.text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.cache.Caching;
import de.atlasmc.cache.MapCache;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.command.argparser.VarArgParser;
import de.atlasmc.command.suggestion.SuggestionType;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializeable;
import de.atlasmc.util.configuration.InvalidConfigurationException;
import de.atlasmc.util.configuration.file.YamlConfiguration;
import de.atlasmc.util.map.ConcurrentLinkedListMultimap;
import de.atlasmc.util.map.Multimap;

public class Commands {
	
	private static final Map<String, Command> commands;
	private static final Map<String, Command> commandByAlias;
	private static final Multimap<Plugin, Command> pluginCommands;
	private static final MapCache<CommandSender, CompletableFuture<CommandContext>> confirmations;
	
	static {
		commands = new ConcurrentHashMap<>();
		commandByAlias = new ConcurrentHashMap<>();
		pluginCommands = new ConcurrentLinkedListMultimap<>();
		confirmations = new MapCache<>();
		Caching.register(confirmations);
	}
	
	public static void register(Command cmd, Plugin plugin) {
		if (cmd == null)
			throw new IllegalArgumentException("Command can not be null!");
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		if (commands.containsKey(cmd.getName()))
			throw new IllegalArgumentException("Command with the given name already registered: " + cmd.getName());
		commands.putIfAbsent(cmd.getName(), cmd);
		pluginCommands.put(plugin, cmd);
		Collection<String> aliases = cmd.getAliases();
		if (!aliases.isEmpty()) {
			for (String alias : aliases)
				commandByAlias.putIfAbsent(alias, cmd);
		}
 	}
	
	public static void register(Collection<Command> commands, Plugin plugin) {
		if (commands == null)
			throw new IllegalArgumentException("Commands can not be null!");
		if (commands.isEmpty())
			return;
		for (Command cmd : commands) {
			register(cmd, plugin);
		}
	}
	
	/**
	 * Loads a command from the given {@link ConfigurationSection}
	 * @param config 
	 * @param inheritPermission
	 * @param inheritSourceValidators
	 * @return command
	 * @throws InvalidConfigurationException
	 */
	public static Command loadCommand(ConfigurationSection config) {
		if (config == null)
			throw new IllegalArgumentException("config can not be null!");
		String cmdName = config.getString("name");
		if (cmdName == null)
			throw new InvalidConfigurationException("\"name\" is not defined!", config);
		Command command = new Command(cmdName);
		String cmdDescription = config.getString("cmd-description");
		command.setCommandDescription(cmdDescription);
		List<String> aliaes = config.getStringList("aliases");
		Map<String, CommandArg> templates = Map.of();
		List<ConfigurationSection> templateCfgs = config.getListOfType("templates", ConfigurationSection.class);
		if (templateCfgs != null) {
			try {
				templates = new HashMap<>();
				for (ConfigurationSection argCfg : templateCfgs) {
					String key = argCfg.getString("template-name");
					if (key == null)
						continue;
					CommandArg template = loadArg(argCfg, templates, null, null);
					if (template == null)
						continue;
					templates.put(key, template);
				}
			} catch(Exception e) {
				throw new InvalidConfigurationException("Error while loading templates for command: " + cmdName, e);
			}
		}
		if (aliaes != null)
			command.getAliases().addAll(aliaes);
		try {
			loadArg(config, command, templates, null, null);
		} catch(Exception e) {
			throw new InvalidConfigurationException("Error while loading command: " + cmdName, e);
		}
		return command;
	}
	
	/**
	 * Loads a commands from the given file.
	 * @param file
	 * @return commands or empty collection
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidConfigurationException
	 */
	public static Collection<Command> loadCommands(File file) throws IOException {
		if (file == null)
			throw new IllegalArgumentException("File can not be null!");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		List<ConfigurationSection> rawCommands = config.getListOfType("commands", ConfigurationSection.class);
		List<Command> commands = new ArrayList<>();
		for (ConfigurationSection rawCommand : rawCommands) {
			Command command = loadCommand(rawCommand);
			if (command != null)
				commands.add(command);
		}
		return commands;
	}
	
	private static CommandArg loadArg(ConfigurationSection config, Map<String, CommandArg> templates, String permission, CommandSourceValidator validator) {
		String tempKey = config.getString("template");
		if (tempKey != null)
			return templates.get(tempKey);
		String type = config.getString("type");
		if (type == null)
			return null;
		CommandArg arg = null;
		switch (type) {
		case "literal":
			arg = loadLiteralArg(config, templates, permission, validator);
			break;
		case "var":
			arg = loadVarArg(config, templates, permission, validator);
			break;
		}
		return arg;
	}
	
	private static void loadArg(ConfigurationSection config, CommandArg arg, Map<String, CommandArg> templates, String permission, CommandSourceValidator validator) {
		if (config.contains("permission")) {
			permission = config.getString("permission");
		}
		arg.setPermission(permission);
		String executorKey = config.getString("executor");
		if (executorKey != null) {
			Registry<CommandExecutor> registry = Registries.getRegistry(CommandExecutor.class);
			CommandExecutor executor = registry.get(executorKey);
			arg.setExecutor(executor);
			if (executor == null)
				throw new InvalidConfigurationException("No executor with name: " + executorKey);
		}
		List<ConfigurationSection> literalArgs = config.getListOfType("args", ConfigurationSection.class);
		if (literalArgs != null) {
			for (ConfigurationSection argCfg : literalArgs) {
				CommandArg childArg = loadArg(argCfg, templates, permission, validator);
				if (childArg != null) {
					arg.setArg(childArg);
				}
			}
		}
		String description = config.getString("description");
		arg.setDescription(description);
		if (config.contains("allowed-source")) {
			String allowedSource = config.getString("allowed-source");
			if (allowedSource != null) {
				CommandSourceValidator newvalidator = Registries.getValue(CommandSourceValidator.class, allowedSource);
				if (newvalidator != null)
					validator = newvalidator;
			}
		}
		arg.setSourceValidator(validator);
	}
	
	private static LiteralCommandArg loadLiteralArg(ConfigurationSection config, Map<String, CommandArg> templates, String permission, CommandSourceValidator validator) {
		String argName = config.getString("name");
		if (argName == null)
			throw new InvalidConfigurationException("\"name\" is not defined!", config);
		LiteralCommandArg arg = new LiteralCommandArg(argName);
		List<String> aliaes = config.getStringList("aliases");
		if (aliaes != null)
			arg.getAliases().addAll(aliaes);
		arg.setArgKey(config.getString("arg-key"));
		loadArg(config, arg, templates, permission, validator);
		return arg;
	}
	
	@SuppressWarnings("rawtypes")
	private static VarCommandArg loadVarArg(ConfigurationSection config, Map<String, CommandArg> templates, String permission, CommandSourceValidator validator) {
		String argName = config.getString("name");
		if (argName == null)
			throw new InvalidConfigurationException("\"name\" is not defined!", config);
		VarCommandArg arg = new VarCommandArg(argName);
		loadArg(config, arg, templates, permission, validator);
		// load parser
		Object rawParser = config.get("parser");
		if (rawParser == null)
			throw new InvalidConfigurationException("\"parser\" is not defined!", config);
		Registry<Class<? extends VarArgParser>> parserRegistry = Registries.getRegistry(VarArgParser.class);
		Class<? extends VarArgParser> parserClass = null;
		ConfigurationSection parserCfg = null;
		if (rawParser instanceof String parserKey) {
			parserClass = parserRegistry.get(parserKey);
			if (parserClass == null)
				throw new InvalidConfigurationException("Unable to find the defined parser: " + parserKey);
			parserCfg = Configuration.of();
		} else if (rawParser instanceof ConfigurationSection parserConfig) {
			String parserKey = parserConfig.getString("type");
			if (parserKey != null) {
				parserClass = parserRegistry.get(parserKey);
				if (parserClass == null)
					throw new InvalidConfigurationException("Unable to find the defined parser: " + parserKey);
			} else
				throw new InvalidConfigurationException("\"type\" is not defined!", config);
			parserCfg = parserConfig;
		} else {
			throw new InvalidConfigurationException("\"parser\" must be a String or Object!", config);
		}
		VarArgParser<?> parser = ConfigurationSerializeable.deserialize(parserCfg, parserClass);
		arg.setParser(parser);
		// Load suggestion type
		Object rawSuggestionType = config.getString("suggestion");
		if (rawSuggestionType != null) {
			String suggestionKey = null;
			ConfigurationSection suggestionCfg = null;
			if (rawParser instanceof String key) {
				suggestionKey = key;
				suggestionCfg = Configuration.of();
			} else if (rawParser instanceof ConfigurationSection suggestionConfig) {
				suggestionKey = suggestionConfig.getString("type");
				suggestionCfg = suggestionConfig;
			}
			if (suggestionKey != null) {
				switch (suggestionKey) {
				case "minecraft:all_recipes":
					arg.setSuggestion(SuggestionType.ALL_RECIPES);
					break;
				case "minecraft:available_sounds":
					arg.setSuggestion(SuggestionType.AVAILABLE_SOUNDS);
					break;
				case "minecraft:summonable_entities":
					arg.setSuggestion(SuggestionType.SUMMONABLE_ENTITIES);
					break;
				default:
					Registry<Class<? extends SuggestionType>> registry = Registries.getRegistry(SuggestionType.class);
					Class<? extends SuggestionType> suggestionClass = registry.get(suggestionKey);
					if (suggestionClass != null) {
						try {
							SuggestionType suggestion = suggestionClass.getConstructor(ConfigurationSection.class).newInstance(suggestionCfg);
							arg.setSuggestion(suggestion);
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException | NoSuchMethodException | SecurityException e) {
							throw new InvalidConfigurationException("Error while instaciating SuggestionType", e, config);
						}
					} else {
						throw new InvalidConfigurationException("Unable to find the defined suggestion type: " + suggestionKey, config);
					}
					break;
				}
			}
		}
		return arg;
	}
	
	public static Future<CommandContext> awaitConfirm(CommandSender sender) {
		CompletableFuture<CommandContext> future = new CompletableFuture<>();
		confirmations.put(sender, future);
		return future;
	}
	
	public static Future<CommandContext> awaitConfirm(CommandSender sender, int ttl) {
		CompletableFuture<CommandContext> future = new CompletableFuture<>();
		confirmations.put(sender, future, ttl);
		return future;
	}
	
	static void finishConfirm(CommandContext context) {
		CommandSender sender = context.getSender();
		CompletableFuture<CommandContext> future = confirmations.get(sender);
		if (future == null) {
			sender.sendMessage("Nothing to confirm...");
			return;
		}
		confirmations.remove(sender, future);
		future.finish(context);
	}
	
	public static Command getCommand(String name) {
		return commands.get(name);
	}
	
	public static Command getByAlias(String alias) {
		return commandByAlias.get(alias);
	}
	
	public static Collection<Command> getCommands() {
		return commands.values();
	}
	
	public static boolean unregister(Command cmd) {
		if (cmd == null)
			return false;
		if (!commands.remove(cmd.getName(), cmd))
			return false;
		Collection<String> aliases = cmd.getAliases();
		if (!aliases.isEmpty()) {
			for (String alias : aliases)
				commandByAlias.remove(alias, cmd);
		}
		return true;
	}
	
	public static CommandContext parseCommand(CommandSender sender, String command) {
		if (sender == null)
			throw new IllegalArgumentException("Sender can not be null!");
		if (command == null)
			throw new IllegalArgumentException("Command can not be unll!");
		CommandStringReader reader = new CommandStringReader(command);
		String rawCommand = reader.readUnquotedString();
		Command cmd = commands.get(rawCommand);
		if (cmd == null)
			cmd = commandByAlias.get(rawCommand);
		if (cmd == null)
			throw new CommandNotFoundException(rawCommand);
		CommandContextBuilder builder = new CommandContextBuilder();
		builder.setSender(sender);
		builder.setCommand(cmd);
		builder.setRawCommand(rawCommand);
		CommandArg currentArg = cmd;
		while (currentArg != null) {
			reader.skipWhitespaces();
			// lookup literals
			if (currentArg.hasLiteralArgs()) {
				final int cursor = reader.getCursor();
				String rawArg = reader.readUnquotedString();
				LiteralCommandArg next = currentArg.getLiteralArg(rawArg);
				if (next != null) {
					String key = next.getArgKey();
					if (key != null)
						builder.addArgument(key, next.getName());
					builder.updateArguments(next);
					currentArg = next;
					continue;
				}
				reader.setCursor(cursor);
			}
			// lookup var args
			if (currentArg.hasVarArgs()) {
				Collection<VarCommandArg> varArgs = currentArg.getVarArgs();
				VarCommandArg next = null;
				for (VarCommandArg varArg : varArgs) {
					final int cursor = reader.getCursor();
					try {
						VarArgParser<?> parser = varArg.getParser();
						if (parser == null)
							continue;
						Object arg = parser.parse(reader);
						if (arg == null)
							continue;
						builder
						.addArgument(varArg.getName(), arg)
						.updateArguments(varArg);
						next = varArg;
						break;
					} catch (CommandSyntaxException e) {
						// TODO handle exception
						reader.setCursor(cursor);
						continue;
					}
				}
				if (next != null) {
					builder.updateArguments(next);
					currentArg = next;
					continue;
				}
			}
			// TODO implement redirects
			break; // break no node found
		}
		return builder.build();
	}
	
	public static List<Chat> buildHelp(int page, int linesPerPage) {
		if (page < 1)
			throw new IllegalArgumentException("Page can not be lower than 1: " + page);
		List<Command> commands = new ArrayList<>(Commands.getCommands());
		commands.sort((a, b) -> {
			return a.getName().compareTo(b.getName());
		});
		int first = (page - 1) * linesPerPage;
		int maxPages = Math.ceilDiv(commands.size(), linesPerPage);
		first = Math.clamp(first, 0, commands.size()-1);
		final int size = ((commands.size()-first) % linesPerPage) + 1;
		List<Chat> lines = new ArrayList<>(size);
		lines.add(chat(
				text("--- ").setColor(ChatColor.DARK_GRAY), 
				text("Help").setColor(ChatColor.RED), 
				text(" - ").setColor(ChatColor.DARK_GRAY), 
				text("Commands").setColor(ChatColor.RED), 
				text(" - ").setColor(ChatColor.DARK_GRAY), 
				text("Page").setColor(ChatColor.RED),
				text(" [").setColor(ChatColor.DARK_GRAY),
				text(Integer.toString(page)).setColor(ChatColor.RED),
				text("/").setColor(ChatColor.DARK_GRAY),
				text(Integer.toString(maxPages)).setColor(ChatColor.RED),
				text("] ---").setColor(ChatColor.DARK_GRAY)));
		for (int i = 1; i < size; i++) {
			Command cmd = commands.get(first++);
			String description = cmd.getCommandDescription();
			if (description == null)
				description = "-";
			lines.add(chat(
					text("- ").setColor(ChatColor.DARK_GRAY),
					text("/").setColor(ChatColor.RED),
					text(cmd.getName()).setColor(ChatColor.DARK_GRAY),
					text(" : ").setColor(ChatColor.RED),
					text(description).setColor(ChatColor.GRAY)));
		}
		return lines;
	}
	
	public static List<Chat> buildHelp(int page, int linesPerPage, Command command) {
		if (page < 1)
			throw new IllegalArgumentException("Page can not be lower than 1: " + page);
		if (command == null)
			throw new IllegalArgumentException("Command can not be null!");
		int first = (page - 1) * linesPerPage;
		int last = first + linesPerPage;
		List<Chat> help = new ArrayList<>();
		ChatComponent base = base();
		help.add(base);
		int size = buildHelp(first, last, 0, help, text("- ").setColor(ChatColor.DARK_GRAY).extra(text("/").setColor(ChatColor.RED)), command);
		int maxPages = Math.ceilDiv(size, linesPerPage);
		base.extra(
				text("--- ").setColor(ChatColor.DARK_GRAY), 
				text("Help").setColor(ChatColor.RED), 
				text(" - ").setColor(ChatColor.DARK_GRAY), 
				text(command.getName()).setColor(ChatColor.RED), 
				text(" - ").setColor(ChatColor.DARK_GRAY), 
				text("Page").setColor(ChatColor.RED),
				text(" [").setColor(ChatColor.DARK_GRAY),
				text(Integer.toString(page)).setColor(ChatColor.RED),
				text("/").setColor(ChatColor.DARK_GRAY),
				text(Integer.toString(maxPages)).setColor(ChatColor.RED),
				text("] ---").setColor(ChatColor.DARK_GRAY));
		return help;
	}
	
	private static int buildHelp(int first, int last, int size, List<Chat> help, ChatComponent parent, CommandArg arg) {
		if (size >= last) {
			return size + getArgSize(arg);
		}
		parent = parent.clone();
		if (!(arg instanceof Command)) {
			parent.extra(space());
		}
		if (arg instanceof VarCommandArg) {
			parent.extra(
					text("<").setColor(ChatColor.RED),
					text(arg.getName()).setColor(ChatColor.DARK_GRAY),
					text(">").setColor(ChatColor.RED));
		} else {
			parent.extra(
					text(arg.getName()).setColor(ChatColor.DARK_GRAY));
		}
		if (arg.hasExecutor()) {
			if (size >= first) {
				String description = arg.getDescription();
				if (description == null) {
					help.add(parent);
				} else {
					help.add(chat(
							parent, 
							text(" : ").setColor(ChatColor.RED), 
							text(arg.getDescription()).setColor(ChatColor.GRAY)));
				}
			}
			size++;
		}
		if (!arg.hasArguments())
			return size;
		for (CommandArg child : arg.getArguments()) {
			if (size >= last) {
				size += getArgSize(child);
			} else {
				size = buildHelp(first, last, size, help, parent, child);
			}
		}
		return size;
	}
	
	private static int getArgSize(CommandArg arg) {
		int size = 0;
		if (arg.hasArguments()) {
			for (CommandArg child : arg.getArguments()) {
				size += getArgSize(child);
			}
		}
		if (arg.hasExecutor()) {
			size++;
		}
		return size;
	}

}
