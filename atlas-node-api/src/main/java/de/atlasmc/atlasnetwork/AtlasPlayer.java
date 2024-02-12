package de.atlasmc.atlasnetwork;

import java.sql.Date;
import java.util.UUID;

import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.command.CommandSender;
import de.atlasmc.entity.Player;
import de.atlasmc.io.protocol.PlayerConnection;
import de.atlasmc.permission.PermissionHandler;

public interface AtlasPlayer extends CommandSender {
	
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

	PermissionHandler getPermissionHandler();

	void setPermissionHandler(PermissionHandler handler);
	
	/**
	 * Returns the connection of this Player if connected to the current Node
	 * @return connection or null
	 */
	PlayerConnection getConnection();
	
	/**
	 * Returns the Player Entity if this Player is connected to a Server of the current Node
	 * @return player or null
	 */
	Player getPlayer();
	
	/**
	 * Returns the internal id of this player
	 * @return id
	 */
	int getID();
	
	Date getFirstJoin();
	
	Date getLastJoin();
	
	void setLastJoin(Date date);

}
