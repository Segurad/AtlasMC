package de.atlasmc.master;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.atlasnetwork.NetworkInfo;
import de.atlasmc.datarepository.Repository;
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
	private static UUID uuid;
	
	protected AtlasMaster() {}
	
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
			database = builder.getDatabase();
			uuid = builder.getUUID();
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

	public static int getOnlinePlayerCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static int getMaxPlayers() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static NetworkInfo getNetworkInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public static NetworkInfo getNetworkInfoMaintenance() {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean isMaintenance() {
		// TODO Auto-generated method stub
		return false;
	}

	public static Collection<Repository> getRepositories() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void setMaxSlots(int slots) {
		// TODO Auto-generated method stub
		
	}

	public static void setMaintenace(boolean maintenance) {
		// TODO Auto-generated method stub
		
	}

	public static void setNetworkInfo(NetworkInfo info) {
		// TODO Auto-generated method stub
		
	}

	public static void setNetworkInfoMaintenance(NetworkInfo info) {
		// TODO Auto-generated method stub
		
	}

	public static UUID getUUID() {
		return uuid;
	}

}
