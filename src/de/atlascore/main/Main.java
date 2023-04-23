package de.atlascore.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.atlascore.CoreAtlasThread;
import de.atlascore.event.inventory.CoreInventoryListener;
import de.atlascore.event.player.CorePlayerListener;
import de.atlascore.io.handshake.CorePacketMinecraftHandshake;
import de.atlascore.io.netty.channel.ChannelInitHandler;
import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlascore.plugin.CoreJavaPluginLoader;
import de.atlascore.plugin.CoreNodeBuilder;
import de.atlascore.plugin.CorePluginManager;
import de.atlascore.proxy.CoreLocalProxy;
import de.atlascore.scheduler.CoreAtlasScheduler;
import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.atlasnetwork.LocalAtlasNode;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.event.Event;
import de.atlasmc.event.EventExecutor;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.Listener;
import de.atlasmc.event.MethodEventExecutor;
import de.atlasmc.io.handshake.HandshakeProtocol;
import de.atlasmc.util.EncryptionUtil;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.file.FileConfiguration;
import de.atlasmc.util.configuration.file.YamlFileConfiguration;

public class Main {
	
	private static boolean OVERRITE_CONFIG;
	
	public static void main(String[] args) {
		OVERRITE_CONFIG = false;
		long startTime = System.currentTimeMillis();
		Logger log = LoggerFactory.getLogger("Atlas");
		System.out.println("\t       ____\n" + 
				"\t      / /  \\     _________   __        ________    ______\n" + 
				"\t     / / /\\ \\   /________/\\ /_/\\      /_______/\\  /_____/\\\n" + 
				"\t    / / /\\ \\ \\  \\__.::.__\\/ \\:\\ \\     \\::: _  \\ \\ \\::::_\\/_\n" + 
				"\t   / / /_/ /\\ \\    \\::\\ \\    \\:\\ \\     \\::(_)  \\ \\ \\:\\/___/\\\n" + 
				"\t  / / /___/ /\\ \\    \\::\\ \\    \\:\\ \\____ \\:: __  \\ \\ \\_::._\\:\\\n" + 
				"\t / /_______/\\ \\ \\    \\::\\ \\    \\:\\/___/\\ \\:.\\ \\  \\ \\  /____\\:\\\n" + 
				"\t/ / /_     __\\ \\_\\    \\__\\/     \\_____\\/  \\__\\/\\__\\/  \\_____\\/\n" + 
				"\t\\_\\___\\   /____/_/ by Segurad\n");
		log.info("VM-Name: {}", System.getProperty("java.vm.name"));
		log.info("VM-Verions: {}", System.getProperty("java.vm.version"));
		log.info("OS-Name: {}", System.getProperty("os.name"));
		File workDir = null;
		for (String arg : args) {
			if (arg.startsWith("atlas.workDir=")) {
				String workDirPath = arg.split("=")[1];
				log.info("Using alternative work dir: {}", workDirPath);
				workDir = new File(workDirPath);
			}
			if (arg.equals("atlas.overrrite.config")) {
				OVERRITE_CONFIG = true;
				log.info("Override configuration enabled!");
			}
		}
		if (workDir == null)
			workDir = new File(System.getProperty("user.dir"));
		if (!workDir.exists())
			workDir.mkdirs();
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
			master = createMaster(log, workDir, masterConfig);
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
		initDefaults(log);
		final CoreNodeBuilder builder = new CoreNodeBuilder(master, log, workDir, config, keyPair);
		log.info("Loading core modules...");
		final CorePluginManager pmanager = new CorePluginManager(log);
		builder.setPluginManager(pmanager);
		builder.setScheduler(new CoreAtlasScheduler());
		builder.setMainThread(new CoreAtlasThread(log));
		builder.setDefaultProtocol(new CoreProtocolAdapter());
		pmanager.addLoader(new CoreJavaPluginLoader());
		File coremodulDir = new File(workDir, "modules/");
		coremodulDir.mkdirs();
		pmanager.loadPlugins(coremodulDir);
		LocalAtlasNode node = builder.build(true);
		
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
			CoreLocalProxy proxy = new CoreLocalProxy(node, port, cfg.clone());
			proxy.setChannelInitHandler(new ChannelInitHandler(proxy));
			proxy.run();
		}
		long endTime = System.currentTimeMillis();
		double timeTotal = (endTime-startTime) / 1000.0;
		log.info("Node up and running after {} seconds", String.format("%,.3f", timeTotal));
		builder.getMainThread().start();
	}

	private static AtlasNetwork connectRemoteMaster(Logger log, String masterHost, int masterPort, KeyPair keyPair) {
		log.error("Remote master is currently not implemented in AtlasMC...");
		return null;
	}

	private static AtlasNetwork createMaster(Logger log, File workDir, ConfigurationSection masterConfig) {
		log.info("Initialize Atlas Master Node...");
		if (masterConfig == null) {
			log.error("Master configuration not found");
			return null;
		}
		MasterBuilder builder = new MasterBuilder(log, workDir, masterConfig);
		return builder.build();
	}
	
	static File extractConfiguration(File destDir, String filename, String resource) throws IOException {
		File file = new File(destDir, filename);
		if (file.exists()) {
			if (OVERRITE_CONFIG)
				file.delete();
			else
				return file;
		}
		InputStream resourceStream = Main.class.getResourceAsStream(resource);
		Files.copy(resourceStream, file.toPath());
		return file;
	}

	private static FileConfiguration loadConfig(File workDir) throws IOException {
		File nodeConfigFile = extractConfiguration(workDir, "node.yml", "/node.yml");
		return YamlFileConfiguration.loadConfiguration(nodeConfigFile);
	}
	
	private static void initDefaults(Logger log) {
		log.info("Loading defaults...");
		initDefaultExecutors(log);
	}

	private static void initDefaultExecutors(Logger log) {
		log.debug("Loading player event default executors...");
		initDefaultExecutor(log, new CorePlayerListener());
		log.debug("Loading inventory event default executors...");
		initDefaultExecutor(log, new CoreInventoryListener());
	}
	
	private static void initDefaultExecutor(Logger log, Listener listener) {
		List<EventExecutor> exes = MethodEventExecutor.getExecutors(listener);
		for (EventExecutor exe : exes) {
			Class<? extends Event> clazz = exe.getEventClass();
			log.debug("Set default executor for event: {}", clazz.getSimpleName());
			HandlerList handlers = HandlerList.getHandlerListOf(clazz);
			handlers.setDefaultExecutor(exe);
		}
	}
	
	private static KeyPair getKeyPair(File workDir, Logger log) {
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
				log.error("Error while wiriting public key file!", e);
			} finally {
				try {
					out.close();
				} catch (IOException e) {}
			}
			log.info("\n\n{}\n", publicKey);
			return pair;
		}
	}

}
