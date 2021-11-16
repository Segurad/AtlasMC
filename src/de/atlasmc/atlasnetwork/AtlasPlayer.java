package de.atlasmc.atlasnetwork;

import java.util.UUID;

import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.Server;

public interface AtlasPlayer {
	
	/**
	 * The original player name
	 * @return player's name
	 */
	public String getName();
	
	/**
	 * The original player uuid
	 * @return player's uuid
	 */
	public UUID getUUID();
	
	/**
	 * Returns the Proxy the player connected to the network
	 * @return proxy
	 */
	public Proxy getOriginProxy();
	
	public Proxy getProxy();
	
	public boolean sendToServer(Server server);
	
	public Server getCurrentServer();
	
	/**
	 * Returns the UUID used to identify a profile
	 * @return uuid
	 */
	public UUID getInteranlUUID();
	
	/**
	 * Sets the UUID used to identify a profile<br>
	 * only updated on reconnect
	 * @param uuid
	 */
	public void setInternalUUID(UUID uuid);
	
	/**
	 * Returns the name of the player profile 
	 * @return name
	 */
	public String getInternalName();
	
	/**
	 * Sets the name of the player profile<br>
	 * only updated on reconnect
	 * @param name
	 */
	public void setInternalName(String name);

}
