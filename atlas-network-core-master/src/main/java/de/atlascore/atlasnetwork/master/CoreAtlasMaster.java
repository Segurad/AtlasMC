package de.atlascore.atlasnetwork.master;

import de.atlasmc.log.Log;
import de.atlasmc.log.Logging;

public class CoreAtlasMaster {
	
	private static Log logger;
	private static CoreAtlasMasterThread thread;
	private static CoreAtlasNetwork network;
	
	public static void init(CoreAtlasNetwork network) {
		if (network == null)
			throw new IllegalArgumentException("Network can not be null!");
		if (CoreAtlasMaster.network != null)
			throw new IllegalStateException("Master already initialized");
		synchronized (CoreAtlasMaster.class) {
			if (CoreAtlasMaster.network != null)
				throw new IllegalStateException("Master already initialized");
			CoreAtlasMaster.network = network;
			CoreAtlasMaster.logger = Logging.getLogger("Atlas-Master", "Atlas-Master");
			CoreAtlasMaster.thread = new CoreAtlasMasterThread(network, logger);
		}
	}
	
	public static Log getLogger() {
		return logger;
	}
	
	public static CoreAtlasMasterThread getThread() {
		return thread;
	}
	
	public static CoreAtlasNetwork getNetwork() {
		return network;
	}
	

}
