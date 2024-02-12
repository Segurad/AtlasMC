package de.atlasmc.atlasnetwork;

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
	
}
