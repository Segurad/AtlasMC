package de.atlasmc.master;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import de.atlasmc.datarepository.Repository;
import de.atlasmc.log.Log;
import de.atlasmc.master.node.NodeManager;
import de.atlasmc.master.server.ServerManager;
import de.atlasmc.master.socket.SocketManager;
import de.atlasmc.network.NetworkInfo;
import de.atlasmc.util.sql.SQLConnectionPool;

public class AtlasMaster {

	private static SocketManager socketManager;
	private static ServerManager serverManager;
	private static NodeManager nodeManager;
	private static Log logger;
	private static PermissionManager permissionManager;
	private static ProfileManager profileManager;
	private static boolean init;
	private static SQLConnectionPool database;
	private static UUID uuid;
	private static volatile NetworkInfo networkInfo;
	private static volatile NetworkInfo networkInfoMaintenance;
	private static volatile boolean maintenance;
	private static volatile int maxSlots;
	
	protected AtlasMaster() {}
	
	static void init(AtlasMasterBuilder builder) {
		synchronized (AtlasMaster.class) {
			if (init)
				throw new IllegalStateException("Master already initialized!");
			socketManager = Objects.requireNonNull(builder.getSocketManager());
			serverManager = Objects.requireNonNull(builder.getServerManager());
			nodeManager = Objects.requireNonNull(builder.getNodeManager());
			logger = Objects.requireNonNull(builder.getLogger());
			permissionManager = Objects.requireNonNull(builder.getPermissionManager());
			profileManager = Objects.requireNonNull(builder.getProfileManager());
			database = Objects.requireNonNull(builder.getDatabase());
			uuid = Objects.requireNonNull(builder.getUUID());
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
	
	public static SocketManager getSocketManager() {
		return socketManager;
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

	public static int getMaxSlots() {
		return maxSlots;
	}

	public static NetworkInfo getNetworkInfo() {
		return networkInfo;
	}

	public static NetworkInfo getNetworkInfoMaintenance() {
		return networkInfoMaintenance;
	}

	public static boolean isMaintenance() {
		return maintenance;
	}

	public static Collection<Repository> getRepositories() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void setMaxSlots(int slots) {
		AtlasMaster.maxSlots = slots;
	}

	public static void setMaintenace(boolean maintenance) {
		AtlasMaster.maintenance = maintenance;
	}

	public static void setNetworkInfo(NetworkInfo info) {
		AtlasMaster.networkInfo = info;
	}

	public static void setNetworkInfoMaintenance(NetworkInfo info) {
		AtlasMaster.networkInfoMaintenance = info;
	}

	public static UUID getUUID() {
		return uuid;
	}

}
