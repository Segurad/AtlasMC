package de.atlasmc.atlasnetwork;

import java.net.InetAddress;
import java.sql.Date;
import java.util.UUID;
import java.util.concurrent.Future;

import de.atlasmc.atlasnetwork.proxy.Proxy;
import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.chat.Messageable;
import de.atlasmc.permission.Permissible;
import de.atlasmc.permission.PermissionHandler;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public interface AtlasPlayer extends Messageable, Permissible {
	
	/**
	 * The original player name
	 * @return player's name
	 */
	@NotNull
	String getMojangName();
	
	/**
	 * The original player uuid
	 * @return player's uuid
	 */
	@NotNull
	UUID getMojangUUID();
	
	/**
	 * Returns the Proxy the player connected to the network
	 * @return proxy
	 */
	@Nullable
	Proxy getOriginProxy();
	
	/**
	 * Returns the Proxy the player is currently at e.g. of when connected to another node in the network.
	 * May be the same as {@link #getOriginProxy()}
	 * @return proxy
	 */
	@Nullable
	Proxy getProxy();
	
	/**
	 * Returns whether or not the player is online.
	 * @return true if online
	 */
	boolean isOnline();
	
	/**
	 * Returns the {@link InetAddress} the player has used for connection or null if not online.
	 * @return address or null
	 */
	@Nullable
	InetAddress getIPAddress();
	
	/**
	 * Try to send this player to another server
	 * @param server
	 * @return future success
	 */
	@NotNull
	Future<Boolean> sendToServer(Server server);
	
	/**
	 * Returns the server the player is currently connected to.
	 * @return server
	 */
	@Nullable
	Server getCurrentServer();
	
	/**
	 * Returns the UUID used to identify a profile.
	 * This id should be used instead of the {@link #getMojangUUID()} for saving data to the player profile.
	 * Atlas maintains this UUID to allow mapping profiles to game accounts.
	 * @return uuid
	 */
	@NotNull
	UUID getInternalUUID();
	
	/**
	 * Returns the name of the player profile 
	 * @return name
	 */
	@NotNull
	String getInternalName();
	
	/**
	 * Sets the name of the player profile<br>
	 * only updated on reconnect
	 * @param name
	 */
	void setInternalName(String name);
	
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
	
	/**
	 * Updates the last join date to the given date
	 * @param date
	 */
	void setLastJoinDate(Date date);

	/**
	 * Returns the permission handler of the player
	 * @return handler
	 */
	PermissionHandler getPermissionHandler();

}
