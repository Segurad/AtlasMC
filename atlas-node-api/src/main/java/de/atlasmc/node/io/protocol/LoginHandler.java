package de.atlasmc.node.io.protocol;

import java.util.UUID;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.connection.ServerSocketConnectionHandler;
import de.atlasmc.node.io.socket.NodeSocket;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.concurrent.future.Future;

public interface LoginHandler {
	
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
	 * The message send for disconnect
	 * @return message
	 */
	@Nullable
	Chat getDisconnectMessage();
	
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
	 * @param authentication whether or not mojang authentication should be performed
	 * @return future
	 */
	@NotNull
	Future<Boolean> enableEncryption(boolean authentication);
	
	/**
	 * Whether or not encryption is enabled
	 * @return true enabled
	 */
	default boolean hasEncryption() {
		return getConnection().isEncryotionEnabled();
	}

}
