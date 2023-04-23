package de.atlasmc.atlasnetwork;

import java.util.UUID;

import de.atlasmc.atlasnetwork.proxy.ProxyConfig;
import de.atlasmc.atlasnetwork.server.ServerGroup;

public interface AtlasNetwork {
	
	ServerGroup getFallBack();
	
	ServerGroup getServerGroup(String name);
	
	NodeConfig getNodeConfig(String name);
	
	ProxyConfig getProxyConfig(String name);

	int getOnlinePlayerCount();
	
	/**
	 * Returns the player profile of a player with the internal name
	 * @param name
	 * @return player or null
	 */
	AtlasPlayer getPlayer(String name);
	
	/**
	 * Returns the player profile of a player with the mojang uuid
	 * @param uuid
	 * @return player or null
	 */
	AtlasPlayer getPlayerByMojang(UUID uuid);
	
	/**
	 * Returns the player profile of a player with the mojang name
	 * @param name
	 * @return player or null
	 */
	AtlasPlayer getPlayerByMojang(String name);
	
	/**
	 * Return the player profile of a player with the internal id
	 * @param id
	 * @return player or null
	 */
	AtlasPlayer getPlayer(int id);

	NetworkInfo getNetworkInfo();

	NetworkInfo getNetworkInfoMaintenance();

	boolean isMainenance();	
}
