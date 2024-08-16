package de.atlasmc.master;

import de.atlasmc.log.Log;
import de.atlasmc.master.node.NodeManager;
import de.atlasmc.master.proxy.ProxyManager;
import de.atlasmc.master.server.ServerManager;
import de.atlasmc.util.sql.SQLConnectionPool;

public class AtlasMaster {

	private static ProxyManager proxyManager;
	private static ServerManager serverManager;
	private static NodeManager nodeManager;
	private static Log logger;
	private static PermissionManager permissionManager;
	private static ProfileManager profileManager;
	private static boolean init;
	private static SQLConnectionPool database;
	
	static void init(AtlasMasterBuilder builder) {
		synchronized (AtlasMaster.class) {
			if (init)
				throw new IllegalStateException("Master already initialized!");
			proxyManager = builder.getProxyManager();
			serverManager = builder.getServerManager();
			nodeManager = builder.getNodeManager();
			logger = builder.getLogger();
			permissionManager = builder.getPermissionManager();
			profileManager = builder.getProfileManager();
		}
	}
	
	public static SQLConnectionPool getDatabase() {
		return database;
	}
	
	public static Log getLogger() {
		return logger;
	}

	public static NodeManager getNodeManager() {
		return nodeManager;
	}
	
	public static ServerManager getServerManager() {
		return serverManager;
	}
	
	public static ProxyManager getProxyManager() {
		return proxyManager;
	}
	
	public static PermissionManager getPermissionManager() {
		return permissionManager;
	}
	
	public static ProfileManager getProfileManager() {
		return profileManager;
	}

}
