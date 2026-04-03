package de.atlasmc.node.io.protocol;

import java.util.UUID;

import javax.crypto.SecretKey;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.connection.ServerSocketConnectionHandler;
import de.atlasmc.network.player.AtlasPlayer;
import de.atlasmc.node.io.socket.NodeSocket;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.mojang.PlayerProfile;

public interface LoginHandler {
	
	/**
	 * Returns the time stamp the login packet was received
	 * @return time
	 */
	long getLoginTime();
	
	@NotNull
	default NodeSocket getSocket() {
		return (NodeSocket) getConnection().getSocket();
	}
	
	@NotNull
	ServerSocketConnectionHandler getConnection();
	
	/**
	 * The name used in login attempt
	 * @return name
	 */
	@NotNull
	String getLoginName();
	
	/**
	 * The uuid used in login attempt
	 * @return uuid
	 */
	@NotNull
	UUID getLoginUUID();
	
	/**
	 * Checks whether the given token matches the generated token
	 * @return true if matches
	 */
	boolean isValidToken(byte[] token);
	
	/**
	 * The message send for disconnect
	 * @return message
	 */
	@Nullable
	Chat getDisconnectMessage();
	
	/**
	 * Sets whether or not this client should be authenticated when encryption is enabled
	 * @param authentication
	 */
	void setAuthentication(boolean authentication);
	
	/**
	 * Whether or not this client should be authenticated when encryption is enabled
	 * @return true if should
	 */
	boolean hasAuthentication();
	
	/**
	 * Whether or not this client is authenticated successfully
	 * @return true if authenticated
	 */
	boolean isAuthenticated();
	
	void setAuthenticated(boolean authenticated);
	
	/**
	 * Whether or not the connection is closed
	 * @return true if closed
	 */
	boolean isClosed();
	
	/**
	 * Disconnects the client with the given message.
	 * Returns whether or not a disconnect was triggered if false a disconnect already happened.
	 * @param message
	 * @return true if a disconnect was triggered
	 */
	boolean disconnect(Chat message);
	
	/**
	 * Starts the encryption process
	 * @return future
	 */
	@NotNull
	Future<Boolean> enableEncryption();
	
	void enableEncryption(SecretKey key);
	
	@NotNull
	Future<AtlasPlayer> getPlayer();
	
	@Nullable
	PlayerProfile getPlayerProfile();
	
	void setPlayerProfile(PlayerProfile profile);
	
	/**
	 * Whether or not encryption is enabled
	 * @return true enabled
	 */
	default boolean hasEncryption() {
		return getConnection().isEncryotionEnabled();
	}

}
