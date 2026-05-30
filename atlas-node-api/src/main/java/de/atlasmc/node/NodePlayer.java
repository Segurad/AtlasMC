package de.atlasmc.node;

import de.atlasmc.chat.Messageable;
import de.atlasmc.network.player.AtlasPlayer;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.io.protocol.PlayerConnection;
import de.atlasmc.node.io.socket.NodeSocket;
import de.atlasmc.node.server.NodeServer;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public interface NodePlayer extends Messageable {
	
	/**
	 * Returns the player connection if the player is connected to this node.
	 * @return connection
	 */
	@Nullable
	PlayerConnection getConnection();
	
	/**
	 * 
	 * @see PlayerConnection#getPlayer()
	 * @return player
	 */
	@NotNull
	Player getPlayer();
	
	/**
	 * 
	 * @see PlayerConnection#hasPlayer()
	 * @return true if has player
	 */
    boolean hasPlayer();
	
    @NotNull
	AtlasPlayer getAtlasPlayer();
	
    @Nullable
	NodeSocket getProxy();
	
    @Nullable
	NodeServer getLocalServer();

}
