package de.atlasmc.master;

import de.atlasmc.log.Log;
import de.atlasmc.master.node.NodeManager;
import de.atlasmc.master.proxy.ProxyManager;
import de.atlasmc.master.server.ServerManager;
import de.atlasmc.util.Builder;
import de.atlasmc.util.sql.SQLConnectionPool;

public class AtlasMasterBuilder implements Builder<Boolean> {
	
	private ProxyManager proxyManager;
	private ServerManager serverManager;
	private NodeManager nodeManager;
	private Log logger;
	private PermissionManager permissionManager;
	private ProfileManager profileManager;
	private SQLConnectionPool database;

	public SQLConnectionPool getDatabase() {
		return database;
	}
	
	public AtlasMasterBuilder setDatabase(SQLConnectionPool database) {
		this.database = database;
		return this;
	}
	
	public ProxyManager getProxyManager() {
		return proxyManager;
	}

	public AtlasMasterBuilder setProxyManager(ProxyManager proxyManager) {
		this.proxyManager = proxyManager;
		return this;
	}

	public ServerManager getServerManager() {
		return serverManager;
	}

	public AtlasMasterBuilder setServerManager(ServerManager serverManager) {
		this.serverManager = serverManager;
		return this;
	}

	public NodeManager getNodeManager() {
		return nodeManager;
	}

	public AtlasMasterBuilder setNodeManager(NodeManager nodeManager) {
		this.nodeManager = nodeManager;
		return this;
	}

	public Log getLogger() {
		return logger;
	}

	public AtlasMasterBuilder setLogger(Log logger) {
		this.logger = logger;
		return this;
	}

	public PermissionManager getPermissionManager() {
		return permissionManager;
	}

	public AtlasMasterBuilder setPermissionManager(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
		return this;
	}

	public ProfileManager getProfileManager() {
		return profileManager;
	}

	public void setProfileManager(ProfileManager profileManager) {
		this.profileManager = profileManager;
	}

	@Override
	public Boolean build() {
		AtlasMaster.init(this);
		return true;
	}

	@Override
	public void clear() {
		logger = null;
		nodeManager = null;
		permissionManager = null;
		profileManager = null;
		proxyManager = null;
		serverManager = null;
	}

}
