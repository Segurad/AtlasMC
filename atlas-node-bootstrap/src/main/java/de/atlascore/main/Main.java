package de.atlascore.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import de.atlascore.CoreAtlasThread;
import de.atlascore.atlasnetwork.master.CoreMasterBuilder;
import de.atlascore.command.CoreConsoleCommandSender;
import de.atlascore.datarepository.CoreCacheRepository;
import de.atlascore.datarepository.CoreDataRepositoryHandler;
import de.atlascore.datarepository.CoreLocalRepository;
import de.atlascore.event.command.CoreCommandListener;
import de.atlascore.event.inventory.CoreInventoryListener;
import de.atlascore.event.player.CorePlayerListener;
import de.atlascore.factory.CoreChatFactory;
import de.atlascore.io.handshake.CorePacketMinecraftHandshake;
import de.atlascore.io.netty.channel.ChannelInitHandler;
import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlascore.log.CoreLogHandler;
import de.atlascore.plugin.CoreJavaPluginLoader;
import de.atlascore.plugin.CoreNodeBuilder;
import de.atlascore.plugin.CorePluginManager;
import de.atlascore.proxy.CoreLocalProxy;
import de.atlascore.registry.CoreRegistryHandler;
import de.atlascore.scheduler.CoreAtlasScheduler;
import de.atlascore.system.init.ContainerFactoryLoader;
import de.atlascore.system.init.EntityTypeLoader;
import de.atlascore.system.init.MaterialLoader;
import de.atlascore.system.init.PotionEffectTypeLoader;
import de.atlascore.world.ChunkWorker;
import de.atlasmc.NamespacedKey;
import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.atlasnetwork.AtlasNode.NodeStatus;
import de.atlasmc.atlasnetwork.LocalAtlasNode;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.command.Command;
import de.atlasmc.command.Commands;
import de.atlasmc.datarepository.LocalRepository;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.event.Event;
import de.atlasmc.event.EventExecutor;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.Listener;
import de.atlasmc.event.MethodEventExecutor;
import de.atlasmc.event.command.CommandEvent;
import de.atlasmc.event.node.NodeInitializedEvent;
import de.atlasmc.io.handshake.HandshakeProtocol;
import de.atlasmc.log.Log;
import de.atlasmc.log.Logging;
import de.atlasmc.plugin.CoremodulPlugin;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryHandler;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.EncryptionUtil;
import de.atlasmc.util.FileUtils;
import de.atlasmc.util.ThreadWatchdog;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.file.FileConfiguration;
import de.atlasmc.util.configuration.file.YamlConfiguration;

public class Main {
	
	private static boolean OVERRIDE_CONFIG;
	
	public static void main(String[] args) {
		OVERRIDE_CONFIG = false;
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
		workDir = new File(workDirPath);
		if (!workDir.exists())
			workDir.mkdirs();
		initLogging(workDir);
		ThreadWatchdog.init();
		Log log  = Logging.getLogger("Atlas", "Atlas");
		log.sendToConsole(true);
		log.info("VM-Name: {}", System.getProperty("java.vm.name"));
		log.info("VM-Verions: {}", System.getProperty("java.vm.version"));
		log.info("OS-Name: {}", System.getProperty("os.name"));
		if (altWorkDir)
			log.info("Using alternativ work dir: {}", workDirPath);
		if (arguments.containsKey("atlas.config.override")) {
			OVERRIDE_CONFIG = true;
			log.info("Override configuration enabled!");
		}
		KeyPair keyPair = getKeyPair(workDir, log);
		FileConfiguration config = null;
		try {
			log.info("Loading node.yml...");
			config = loadConfig(workDir);
		} catch (Exception e) {
			log.error("Error while loading node.yml!", e);
		}
		if (config == null) {
			System.exit(1);
		}
		boolean isMaster = config.getBoolean("is-master");
		AtlasNetwork master = null;
		if (isMaster) {
			ConfigurationSection masterConfig = config.getConfigurationSection("master");
			master = createMaster(log, workDir, masterConfig, arguments, keyPair);
		} else {
			String masterHost = config.getString("host");
			int masterPort = config.getInt("port"); 
			master = connectRemoteMaster(log, masterHost, masterPort, keyPair);
		}
		if (master == null) {
			log.error("Unable to initialize master!");
			System.exit(1);
		}
		log.info("Atlas Master initialized");
		log.info("Initizalizing Node...");
		RegistryHandler regHandler = loadRegistry(log);
		Registries.init(regHandler);
		try {
			initDefaults(log);
		} catch (Exception e) {
			log.error("Error while initializing defaults!", e);
		}
		UUID nodeUUID = UUID.randomUUID(); // TODO request node uuid from master
		final CoreNodeBuilder builder = new CoreNodeBuilder(nodeUUID, master, log, workDir, config, keyPair);
		CoreCacheRepository cache = new CoreCacheRepository(new File(workDir, "cache/data/"));
		LocalRepository repo = isMaster ? (LocalRepository) master.getRepository() : new CoreLocalRepository(new File(workDir, "data/"), "local");
		CoreDataRepositoryHandler repoHandler = new CoreDataRepositoryHandler(cache, repo);
		builder.setDataHandler(repoHandler);
		if (!isMaster)
			builder.getDataHandler().addRepo(master.getRepository());
		log.info("Loading core modules...");
		Collection<RepositoryEntry> entries = null;
		try {
			entries = repoHandler.getEntries(builder.getModules()).get();
		} catch (Exception e) {
			log.error("Error while fetching data for modules!", e);
		}
		File tmpCoremodulDir = new File(workDir, "tmp/modules/");
		tmpCoremodulDir.mkdirs();
		if (entries != null) {
			for (RepositoryEntry entry : entries) {
				try {
					entry.copyTo(tmpCoremodulDir);
				} catch (Exception e) {
					log.error("Error while copying data for modules!", e);
				}
			}
		}
		loadCommands(log, workDir);
		final CorePluginManager pmanager = new CorePluginManager(log);
		builder.setChatFactory(new CoreChatFactory());
		builder.setPluginManager(pmanager);
		builder.setScheduler(new CoreAtlasScheduler());
		builder.setMainThread(new CoreAtlasThread(log));
		builder.setDefaultProtocol(new CoreProtocolAdapter());
		pmanager.addLoader(new CoreJavaPluginLoader());
		File coremodulDir = new File(workDir, "modules/");
		coremodulDir.mkdirs();
		pmanager.loadPlugins(coremodulDir);
		pmanager.loadPlugins(tmpCoremodulDir);
		if (pmanager.getPluginCount() > 0) { 
			for (Plugin plugin : pmanager.getPlugins()) {
				if (!(plugin instanceof CoremodulPlugin))
					continue;
				((CoremodulPlugin) plugin).initNode(builder);
			}
		}
		LocalAtlasNode node = builder.build();
		HandshakeProtocol.DEFAULT_PROTOCOL.setPacketIO(0x00, new CorePacketMinecraftHandshake());
		Random rand = null;
		log.info("Initializing Proxy...");
		for (ProxyConfig cfg : builder.getProxyConfigs()) {
			int port = cfg.getPort();
			if (port == -1) {
				if (rand == null)
					rand = new Random();
				port = 25000 + rand.nextInt(10000);
			}
			UUID proxyUUID = UUID.randomUUID(); // TODO request uuid form master
			CoreLocalProxy proxy = new CoreLocalProxy(proxyUUID, node, port, cfg.clone());
			proxy.setChannelInitHandler(new ChannelInitHandler(proxy));
			proxy.run();
			node.registerProxy(proxy);
		}
		CoreConsoleCommandSender console = null;
		try {
			console = new CoreConsoleCommandSender();
		} catch (IOException e) {
			log.error("Error while initializing console sender!", e);
			System.exit(1);
		}
		builder.setConsoleSender(console);
		builder.getMainThread().runTask(() -> {
			HandlerList.callEvent(new NodeInitializedEvent());
		});
		builder.getMainThread().start();
		ThreadWatchdog.watch(builder.getMainThread());
		long endTime = System.currentTimeMillis();
		double timeTotal = (endTime-startTime) / 1000.0;
		log.info("{}-Node up and running after {} seconds", isMaster ? "Master" : "Minion", String.format("%,.3f", timeTotal));
		while (node.getStatus() == NodeStatus.ONLINE) {
			String command = console.readLine();
			HandlerList.callEvent(new CommandEvent(console, command, true));
		}
		try {
			builder.getMainThread().join();
		} catch (InterruptedException e) {}
	}

	private static void loadCommands(Log log, File workDir) {
		log.info("Loading commands...");
		File commandFile = null;
		try {
			commandFile = FileUtils.extractConfiguration(new File(workDir, "data"), "commands.yml", "/data/commands.yml", OVERRIDE_CONFIG, Main.class);
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

	private static AtlasNetwork connectRemoteMaster(Log log, String masterHost, int masterPort, KeyPair keyPair) {
		log.error("Remote master is currently not implemented in AtlasMC...");
		return null;
	}

	private static AtlasNetwork createMaster(Log log, File workDir, ConfigurationSection masterConfig, Map<String, String> arguments, KeyPair keyPair) {
		log.info("Initialize Atlas Master Node...");
		if (masterConfig == null) {
			log.error("Master configuration not found");
			return null;
		}
		CoreMasterBuilder builder = new CoreMasterBuilder(log, workDir, masterConfig, arguments, keyPair);
		return builder.build();
	}

	private static FileConfiguration loadConfig(File workDir) throws IOException {
		File nodeConfigFile = FileUtils.extractConfiguration(workDir, "node.yml", "/node.yml", OVERRIDE_CONFIG, Main.class);
		return YamlConfiguration.loadConfiguration(nodeConfigFile);
	}
	
	private static void initDefaults(Log log) throws Exception {
		log.info("Loading defaults...");
		ChunkWorker.init(ChunkWorker.DEFAULT_THREADS);
		MaterialLoader.loadMaterial();
		ContainerFactoryLoader.loadContainerFactories();
		PotionEffectTypeLoader.loadPotionEffects();
		EntityTypeLoader.loadEntityTypes();
		initDefaultExecutors(log);
	}

	private static void initDefaultExecutors(Log log) {
		log.debug("Loading player event default executors...");
		initDefaultExecutor(log, new CorePlayerListener());
		log.debug("Loading inventory event default executors...");
		initDefaultExecutor(log, new CoreInventoryListener());
		log.debug("Loading command event default executors...");
		initDefaultExecutor(log, new CoreCommandListener());
	}
	
	private static void initDefaultExecutor(Log log, Listener listener) {
		List<EventExecutor> exes = MethodEventExecutor.getExecutors(CorePluginManager.SYSTEM, listener);
		for (EventExecutor exe : exes) {
			Class<? extends Event> clazz = exe.getEventClass();
			log.debug("Set default executor for event: {}", clazz.getSimpleName());
			HandlerList handlers = HandlerList.getHandlerListOf(clazz);
			handlers.setDefaultExecutor(exe);
		}
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
	
	private static void initLogging(File workDir) {
		YamlConfiguration logConfig = null;
		try {
			logConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(Main.class.getResourceAsStream("/log-config.yml")));
		} catch (IOException e) {
			System.out.println("Error while loading log-config.yml!");
			e.printStackTrace();
			System.exit(1);
		}
		Logging.init(new CoreLogHandler(workDir, logConfig));
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
	
	private static RegistryHandler loadRegistry(Log logger) {
		logger.info("Loading registries...");
		RegistryHandler handler = new CoreRegistryHandler();
		YamlConfiguration registryConfig = null;
		try {
			registryConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(Main.class.getResourceAsStream("/META-INF/atlas/registries.yml")));
		} catch(IOException e) {
			logger.error("Error while loading registries", e);
			return handler;
		}
		for (String key : registryConfig.getKeys()) {
			String value = registryConfig.getString(key);
			String[] parts = value.split(":");
			String rawClass = parts[0];
			Target target = Target.valueOf(parts[1]);
			try {
				Class<?> registryType = Class.forName(rawClass);
				handler.createRegistry(NamespacedKey.of(key), registryType, target);
				logger.debug("Created registry ({}) of type: {}", key, rawClass);
			} catch (ClassNotFoundException e) {
				logger.error("Registry ({}) class not found: {}", key, rawClass);
				continue;
			}
		}
		YamlConfiguration registryEntryConfig = null;
		try {
			registryEntryConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(Main.class.getResourceAsStream("/META-INF/atlas/registry-entries.yml")));
		} catch(IOException e) {
			logger.error("Error while loading registry entries", e);
			return handler;
		}
		for (String key : registryEntryConfig.getKeys()) {
			Registry<Object> registry = handler.getRegistry(key);
			if (registry == null) {
				logger.debug("Unable to insert registry entries for missing registry: {}", key);
				continue;
			}
			List<String> rawEntries = registryEntryConfig.getStringList(key);
			for (String rawEntry : rawEntries) {
				String[] parts = rawEntry.split(":", 2);
				String rawClass = parts[0];
				String entryKey = parts[1];
				Class<?> entryClass = null;
				try {
					entryClass = Class.forName(rawClass);
				} catch (ClassNotFoundException e) {
					logger.error("Registry ({}) entry class not found: {}", key, rawClass);
					logger.error(e);
					continue;
				}
				Object entryValue = null;
				if (registry.getTarget() == Target.CLASS) {
					entryValue = entryClass;
				} else {
					try {
						entryValue = entryClass.getConstructor().newInstance();
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException e) {
						logger.error("Unable to create instance of registry entry: " + entryClass.getName(), e);
					}
				}
				if (Registries.DEFAULT_REGISTRY_KEY.equals(entryKey)) {
					registry.setDefault(entryValue);
					logger.debug("Registry ({}) set default: {}", key, entryClass.getName());
				} else if (registry.register(parts[1], entryValue)) {
					logger.debug("Registry ({}) registered: {}={}", key, entryKey, entryClass.getName());
				}
			}
		}
		return handler;
	}

}
