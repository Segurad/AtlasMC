package de.atlasmc.atlasnetwork;

import java.sql.Date;
import java.util.UUID;

import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.chat.Messageable;
import de.atlasmc.permission.Permissible;
import de.atlasmc.permission.PermissionHandler;

public interface AtlasPlayer extends Messageable, Permissible {
	
	/**
	 * The original player name
	 * @return player's name
	 */
	String getMojangName();
	
	/**
	 * The original player uuid
	 * @return player's uuid
	 */
	UUID getMojangUUID();
	
	/**
	 * Returns the Proxy the player connected to the network
	 * @return proxy
	 */
	Proxy getOriginProxy();
	
	/**
	 * Returns the Proxy the player is currently at e.g. of when connected to another node in the network.
	 * May be the same as {@link #getOriginProxy()}
	 * @return proxy
	 */
	Proxy getProxy();
	
	/**
	 * Try to send this player to another server
	 * @param server
	 */
	void sendToServer(Server server);
	
	Server getCurrentServer();
	
	/**
	 * Returns the UUID used to identify a profile
	 * @return uuid
	 */
	UUID getInternalUUID();
	
	/**
	 * Sets the UUID used to identify a profile<br>
	 * only updated on reconnect
	 * @param uuid
	 */
	void setInternalUUID(UUID uuid);
	
	/**
	 * Returns the name of the player profile 
	 * @return name
	 */
	String getInternalName();
	
	/**
	 * Sets the name of the player profile<br>
	 * only updated on reconnect
	 * @param name
	 */
	void setInternalName(String name);
	
	/**
	 * Returns the internal id of this player
	 * @return id
	 */
	int getID();
	
	/**
	 * Returns the date time this player first joined the network
	 * @return date
	 */
	Date getFirstJoin();
	
	/**
	 * Returns the date time this player last joined the network
	 * @return
	 */
	Date getLastJoin();

	PermissionHandler getPermissionHandler();

}
