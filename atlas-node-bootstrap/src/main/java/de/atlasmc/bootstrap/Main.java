package de.atlasmc.bootstrap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import de.atlasmc.Atlas;
import de.atlasmc.AtlasBuilder;
import de.atlasmc.NamespacedKey;
import de.atlasmc.cache.Caching;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.command.Command;
import de.atlasmc.command.Commands;
import de.atlasmc.core.cache.CoreCacheHandler;
import de.atlasmc.core.chat.CoreChatFactory;
import de.atlasmc.core.command.CoreConsoleCommandSender;
import de.atlasmc.core.datarepository.CoreCacheRepository;
import de.atlasmc.core.datarepository.CoreDataRepositoryHandler;
import de.atlasmc.core.datarepository.CoreLocalRepository;
import de.atlasmc.core.log.CoreLogHandler;
import de.atlasmc.core.plugin.CoreJavaPluginLoader;
import de.atlasmc.core.plugin.CorePluginManager;
import de.atlasmc.core.registry.CoreRegistryHandler;
import de.atlasmc.core.scheduler.CoreAtlasScheduler;
import de.atlasmc.datarepository.DataRepositoryHandler;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.command.CommandEvent;
import de.atlasmc.log.Log;
import de.atlasmc.log.Logging;
import de.atlasmc.plugin.AtlasModul;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginManager;
import de.atlasmc.plugin.startup.StartupContext;
import de.atlasmc.plugin.startup.StartupException;
import de.atlasmc.plugin.startup.StartupFinalizedEvent;
import de.atlasmc.plugin.startup.StartupStageHandler;
import de.atlasmc.registry.Registries;
import de.atlasmc.tick.AtlasThread;
import de.atlasmc.util.EncryptionUtil;
import de.atlasmc.util.FileUtils;
import de.atlasmc.util.ThreadWatchdog;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.MemoryConfigurationSection;
import de.atlasmc.util.configuration.file.FileConfiguration;
import de.atlasmc.util.configuration.file.YamlConfiguration;

public class Main {
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		System.out.println("\t       ____\n" + 
				"\t      / /  \\     _________   __        ________    ______\n" + 
				"\t     / / /\\ \\   /________/\\ /_/\\      /_______/\\  /_____/\\\n" + 
				"\t    / / /\\ \\ \\  \\__.::.__\\/ \\:\\ \\     \\::: _  \\ \\ \\::::_\\/_\n" + 
				"\t   / / /_/ /  \\    \\::\\ \\    \\:\\ \\     \\::(_)  \\ \\ \\:\\/___/\\\n" + 
				"\t  / / /___/ /\\ \\    \\::\\ \\    \\:\\ \\____ \\:: __  \\ \\ \\_::._\\:\\\n" + 
				"\t / / ______/\\ \\ \\    \\::\\ \\    \\:\\/___/\\ \\:.\\ \\  \\ \\  /____\\:\\\n" + 
				"\t/ / /_     __\\ \\_\\    \\__\\/     \\_____\\/  \\__\\/\\__\\/  \\_____\\/\n" + 
				"\t\\_\\___\\   /____/_/ by Segurad\n");
		File workDir = null;
		Map<String, String> arguments = getJVMArgs(args);
		String workDirPath = arguments.get("atlas.workDir");
		boolean altWorkDir = false;
		if (workDirPath != null) {
			altWorkDir = true;
		} else {
			workDirPath = System.getProperty("user.dir");
		}
		workDir = new File(workDirPath).getAbsoluteFile();
		if (!workDir.exists())
			workDir.mkdirs();
		System.setProperty("atlas.workDir", workDir.getAbsolutePath());
		AtlasBuilder.initWorkDir(workDir);
		Logging.init(new CoreLogHandler());
		ThreadWatchdog.init();
		Log log  = Logging.getLogger("Atlas", "Atlas");
		log.sendToConsole(true);
		log.info("VM-Name: {}", System.getProperty("java.vm.name"));
		log.info("VM-Verions: {}", System.getProperty("java.vm.version"));
		log.info("VM-Vendor: {}", System.getProperty("java.vendor"));
		log.info("OS-Name: {}", System.getProperty("os.name"));
		Caching.init(new CoreCacheHandler());
		if (altWorkDir)
			log.info("Using alternativ work dir: {}", workDirPath);
		if (FileUtils.CONFIG_OVERRIDE) {
			log.info("Override configuration enabled!");
		}
		KeyPair keyPair = getKeyPair(workDir, log);
		FileConfiguration config = null;
		try {
			log.info("Loading bootstrap.yml...");
			config = loadConfig(workDir);
		} catch (Exception e) {
			log.error("Error while loading bootstrap.yml!", e);
		}
		if (config == null) {
			System.exit(1);
		}
		
		ChatUtil.init(new CoreChatFactory());
		Registries.init(new CoreRegistryHandler());
		
		final CoreConsoleCommandSender console;
		try {
			console = new CoreConsoleCommandSender();
		} catch (IOException e) {
			log.error("Error while initializing console sender!", e);
			System.exit(1);
			return;
		}
		
		PluginManager pluginManager = new CorePluginManager(log);
		pluginManager.addLoader(new CoreJavaPluginLoader());
		
		List<String> rawFeatures = config.getStringList("features", List.of());
		for (String raw : rawFeatures) {
			pluginManager.addFeature(NamespacedKey.literal(raw));
		}
		boolean isMaster = pluginManager.hasFeature(NamespacedKey.of("atlas:master"));
		
		new AtlasBuilder()
		.setDataHandler(loadRepositories(log, workDir))
		.setKeyPair(keyPair)
		.setLogger(log)
		.setMainThread(new AtlasThread<>("Atlas-Main", 50, log, false, null))
		.setPluginManager(pluginManager)
		.setScheduler(new CoreAtlasScheduler())
		.setWorkDir(workDir)
		.setSystem(CorePluginManager.SYSTEM)
		.build();

		StartupContext context = new StartupContext(log);
		context.addStage(
				StartupContext.LOAD_EXTRA_PLUGINS,
				StartupContext.INIT_STAGES,
				StartupContext.CONNECT_MASTER, 
				StartupContext.INIT_NODE,
				StartupContext.LOAD_NODE_DATA,
				StartupContext.FINALIZE_STARTUP);
		if (isMaster) {
			context.addStageAfter(
					StartupContext.INIT_STAGES,
					StartupContext.INIT_MASTER,
					StartupContext.LOAD_MASTER_DATA);
			pluginManager.addFeature(NamespacedKey.literal("atlas:node-master"));
		} else {
			pluginManager.addFeature(NamespacedKey.literal("atlas:node-minion"));
		}
		
		File modulDir = new File(workDir, "modules/");
		
		try {
			ConfigurationSection setup = YamlConfiguration.loadConfiguration(Atlas.getSystem().getResourceAsStream("core-system.yml"));
			FileUtils.runSetupConfiguration(workDir, setup, Atlas.getSystem());
		} catch (Exception e) {
			log.error("Error while running setup!", e);
		}
		
		Registries.loadRegistries(Atlas.getSystem());
		Registries.loadRegistryEntries(Atlas.getSystem());
		
		pluginManager.loadPlugins(modulDir);
		
		Thread consoleDeamon = new Thread(() -> {
			Atlas.getLogger().debug("Started console daemon!");
			while (true) {
				String command = console.readLine();
				HandlerList.callEvent(new CommandEvent(console, command, true));
			}
		}, "Atlas-Console-Daemon");
		consoleDeamon.setDaemon(true);
		loadCommands(log, workDir);
		
		initStartupHandler(log, context, Main.class.getResourceAsStream("/META-INF/atlas/startup-handlers.yml"));
		
		for (Plugin plugin : pluginManager.getPlugins()) {
			if (!(plugin instanceof AtlasModul modul))
				continue;
			if (!plugin.isEnabled())
				continue;
			modul.initStartupHandler(context);
			initStartupHandler(log, context, plugin.getResourceAsStream("/META-INF/atlas/startup-handlers.yml"));
		}
		
		try {
		do {
			log.debug("Running startup stage: {}", context.getStage());
			prepareStage(context);
			runStage(context);
			finalizeStage(context);
		} while (!context.hasFailed() && context.nextStage());
		} catch (StartupException e) {}
		if (context.hasFailed()) {
			Throwable cause = context.getCause();
			log.error("Error while running startup! (stage: " + context.getStage() + ")", cause);
			for (Consumer<Throwable> handler : context.getFailHandlers()) {
				try {
					handler.accept(cause);
				} catch(Exception e) {
					log.error("Error while running startup fail handler!", e);
				}
			}
			System.exit(1);
		}
		Atlas.getMainThread().runTask(() -> {
			HandlerList.callEvent(new StartupFinalizedEvent());
		});
		long endTime = System.currentTimeMillis();
		double timeTotal = (endTime-startTime) / 1000.0;
		log.info("{}-Node up and running after {} seconds", isMaster ? "Master" : "Minion", String.format("%,.3f", timeTotal));

		Atlas.getMainThread().startThread();
		ThreadWatchdog.watch(Atlas.getMainThread());
		consoleDeamon.start();
	}
	
	@SuppressWarnings("unchecked")
	private static void initStartupHandler(Log logger, StartupContext context, InputStream in) {
		if (in == null)
			return;
		logger.info("Loading startup handlers...");
		YamlConfiguration cfg;
		try {
			cfg = YamlConfiguration.loadConfiguration(in);
		} catch (IOException e) {
			logger.error("Error while loading startup-handler.yml!", e);
			System.exit(1);
			return;
		}
		Map<String, StartupStageHandler> initializedHandlers = new HashMap<>();
		for (String key : cfg.getKeys()) {
			List<String> handlers = cfg.getStringList(key);
			for (String rawHandlerClass : handlers) {
				StartupStageHandler handler;
				if (!initializedHandlers.containsKey(rawHandlerClass)) {
					initializedHandlers.put(rawHandlerClass, null);
					Class<?> handlerClass;
					try {
						handlerClass = Class.forName(rawHandlerClass);
					} catch (ClassNotFoundException e) {
						logger.error("Unable to find startup stage handler class for stage: " + key, e);
						continue;
					}
					if (!StartupStageHandler.class.isAssignableFrom(handlerClass)) {
						logger.error("Startup stage handler class is not accessible as StartupStageHandler: {}", rawHandlerClass);
						continue;
					}
					Constructor<? extends StartupStageHandler> newHandler;
					try {
						newHandler = (Constructor<? extends StartupStageHandler>) handlerClass.getDeclaredConstructor();
					} catch (NoSuchMethodException e) {
						logger.error("No constructor found for startup handler: " + handlerClass.getName());
						continue;
					} catch (Exception e) {
						logger.error("Error while fetching constructor for startup handler: " + handlerClass.getName(), e);
						continue;
					}
					newHandler.setAccessible(true);
					try {
						handler = newHandler.newInstance();
					} catch (Exception e) {;
						logger.error("Error while creating new handler instance of: " + handlerClass.getName(), e);
						continue;
					}
					initializedHandlers.put(rawHandlerClass, handler);
				} else {
					handler = initializedHandlers.get(rawHandlerClass);
					logger.debug("Reusing handler {} for stage: {}", rawHandlerClass, key);
				}
				if (handler == null)
					continue;
				context.addStageHandler(key, handler);
				logger.debug("Added startup stage handler {} for stage: {}", rawHandlerClass, key);
			}
		}
	}
	
	private static void prepareStage(StartupContext context) {
		if (context.hasFailed())
			return;
		Collection<StartupStageHandler> handlers = context.getStageHandlers();
		try {
			for (StartupStageHandler handler : handlers) {
				if (context.hasFailed())
					return;
				handler.prepareStage(context);
			}
		} catch (Exception e) {
			context.fail(e);
		}
	}
	
	private static void runStage(StartupContext context) {
		if (context.hasFailed())
			return;
		Collection<StartupStageHandler> handlers = context.getStageHandlers();
		try {
			for (StartupStageHandler handler : handlers) {
				if (context.hasFailed())
					return;
				handler.handleStage(context);
			}
		} catch (Exception e) {
			context.fail(e);
		}
	}
	
	private static void finalizeStage(StartupContext context) {
		if (context.hasFailed())
			return;
		Collection<StartupStageHandler> handlers = context.getStageHandlers();
		try {
			for (StartupStageHandler handler : handlers) {
				if (context.hasFailed())
					break;
				handler.finalizeStage(context);
			}
		} catch (Exception e) {
			context.fail(e);
		}
	}

	private static void loadCommands(Log log, File workDir) {
		log.info("Loading commands...");
		File commandFile = null;
		try {
			commandFile = FileUtils.extractResource(new File(workDir, "data/commands.yml"), "/data/commands.yml", FileUtils.CONFIG_OVERRIDE, Main.class);
		} catch (IOException e) {
			log.error("Error while extracting commands.yml", e);
			return;
		}
		Collection<Command> commands = null;
		try {
			commands = Commands.loadCommands(commandFile);
		} catch (Exception e) {
			log.error("Error while loading commands.yml", e);
			return;
		}
		Commands.register(commands, CorePluginManager.SYSTEM);
		for (Command cmd : commands) {
			if (cmd.hasAliases()) {
				log.debug("Registered command: {} {}", cmd.getName(), cmd.getAliases());
			} else {
				log.debug("Registered command: {}", cmd.getName());
			}
		}
	}

	private static FileConfiguration loadConfig(File workDir) throws IOException {
		File nodeConfigFile = FileUtils.extractResource(new File(workDir, "bootstrap.yml"), "/bootstrap.yml", FileUtils.CONFIG_OVERRIDE, Main.class);
		return YamlConfiguration.loadConfiguration(nodeConfigFile);
	}
	
	private static Map<String, String> getJVMArgs(String[] args) {
		Map<String, String> arguments = null;
		if (args.length == 0) {
			arguments = Map.of();
		} else {
			arguments = new HashMap<>(args.length);
			for (String arg : args) {
				int first = arg.indexOf('=');
				if (first == -1) {
					arguments.put(arg, "true");
					continue;
				}
				String[] splitt = arg.split("=", 2);
				arguments.put(splitt[0], splitt[1]);
			}
			arguments = Map.copyOf(arguments);
		}
		return arguments;
	}
	
	private static KeyPair getKeyPair(File workDir, Log log) {
		File privateKeyFile = new File(workDir, "node-key.key");
		if (privateKeyFile.exists()) {
			log.info("Loading keypair...");
			try {
				return EncryptionUtil.loadRSAFromFile(privateKeyFile.toPath());
			} catch (Exception e) {
				log.error("Error while loading keypair!", e);
				System.exit(1);
			}
			return null;
		} else {
			log.info("Generating keypair...");
			KeyPairGenerator gen = null;
			try {
				gen = KeyPairGenerator.getInstance("RSA");
			} catch (NoSuchAlgorithmException e) {
				log.error("Unable to find RSA key factory!", e);
				System.exit(1);
			}
			gen.initialize(2048);
			KeyPair pair = gen.generateKeyPair();
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(privateKeyFile);
				out.write(pair.getPrivate().getEncoded());
			} catch (IOException e) {
				log.error("Error while writing private key file!", e);
			} finally {
				try {
					out.close();
				} catch (IOException e) {}
			}
			String publicKey = EncryptionUtil.keyToString(pair.getPublic());
			File publicKeyFile = new File(workDir, "node-key.pub");
			try {
				out = new FileOutputStream(publicKeyFile);
				out.write(publicKey.getBytes());
			} catch (IOException e) {
				log.error("Error while writing public key file!", e);
			} finally {
				try {
					out.close();
				} catch (IOException e) {}
			}
			log.info("\n\n{}", publicKey);
			return pair;
		}
	}
	
	private static DataRepositoryHandler loadRepositories(Log logger, File workDir) {
		CoreCacheRepository cache = new CoreCacheRepository(new File(workDir, "cache/data/"));
		CoreDataRepositoryHandler repoHandler = new CoreDataRepositoryHandler(cache);
		File repoMeta = new File(workDir, "data/repositories.yml");
		if (repoMeta.exists()) {
			try {
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(repoMeta);
				List<ConfigurationSection> repos = cfg.getConfigurationList("repositories");
				for (ConfigurationSection repoCfg : repos) {
					String name = repoCfg.getString("name");
					UUID uuid = UUID.fromString(repoCfg.getString("uuid"));
					String path = repoCfg.getString("path");
					File repoPath = new File(workDir, path);
					CoreLocalRepository repo = new CoreLocalRepository(name, uuid, repoPath);
					repoHandler.addRepo(repo);
					logger.debug("Loaded repository {}-{}: {}", name, uuid, repoPath);
				}
			} catch (IOException e) {
				logger.error("Error while loading repositories! (using defaults)", e);
			}
			return repoHandler;
		}
		String repoPath = "data/";
		CoreLocalRepository repo = new CoreLocalRepository("localdata", UUID.randomUUID(), new File(workDir, repoPath));
		repo.registerNamespace("plugins", "plugins");
		repo.registerNamespace("configurations", "configurations");
		repo.registerNamespace("worlds", "worlds");
		repo.registerNamespace("server-templates", "templates/server");
		repo.registerNamespace("schematics", "schematics");
		YamlConfiguration cfg = new YamlConfiguration();
		ArrayList<ConfigurationSection> repos = new ArrayList<>();
		cfg.set("repositories", repos);
		MemoryConfigurationSection repoCfg = new MemoryConfigurationSection(cfg);
		repoCfg.set("name", repo.getName());
		repoCfg.set("uuid", repo.getUUID().toString());
		repoCfg.set("path", repoPath);
		repos.add(repoCfg);
		try {
			cfg.save(repoMeta);
		} catch (IOException e) {
			logger.error("Error whil writing repository meta file!", e);
		}
		repoHandler.addRepo(repo);
		return repoHandler;
	}

}
