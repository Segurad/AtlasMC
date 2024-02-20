package de.atlasmc.atlasnetwork;

import java.security.PublicKey;
import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNode.NodeStatus;
import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.atlasnetwork.server.ServerManager;
import de.atlasmc.datarepository.Repository;
import de.atlasmc.tick.Tickable;

public interface AtlasNetwork extends Tickable {
	
	NodeManager getNodeManager();
	
	ServerManager getServerManager();
	
	ProfileHandler getProfileHandler();
	
	PermissionProvider getPermissionProvider();
	
	NodeConfig getNodeConfig(String name);
	
	ProxyConfig getProxyConfig(String name);

	int getOnlinePlayerCount();

	NetworkInfo getNetworkInfo();

	NetworkInfo getNetworkInfoMaintenance();

	boolean isMaintenance();
	
	Repository getRepository();
	
	/**
	 * Updates the status of this node to the master
	 * @param status to set
	 */
	void updateNodeStatus(NodeStatus status);
	
	/**
	 * Returns this nodes UUID
	 * @return uuid
	 */
	UUID getNodeUUID();
	
	/**
	 * Returns the masters public key
	 * @return public key
	 */
	PublicKey getPublicKey();
	
}
