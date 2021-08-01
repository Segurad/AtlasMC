package de.atlasmc.io.protocol;

import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.chat.ChatMode;
import de.atlasmc.entity.Player;
import de.atlasmc.io.Packet;
import de.atlasmc.util.annotation.ThreadSafe;

public interface PlayerConnection extends PacketListenerPlayIn {
	
	public void queueInboundPacket(Packet packet);
	
	public void handleQueuedPackets();

	public ProtocolAdapter getProtocol();

	/**
	 * 
	 * @return the current server or null if not at this note
	 */
	@ThreadSafe
	public LocalServer getLocalSever();
	
	public int getNextWindowActionID();
	
	public int getNextWindowActionIDAndLock();
	
	/**
	 * Used to bind a {@link Player} Entity to this connection if the player is currently on this {@link AtlasNode}
	 * @param player
	 */
	public void setPlayer(Player player);
	
	/**
	 * 
	 * @return the current {@link Player} Entity bound to this connection or null
	 */
	public Player getPlayer();
	
	public AtlasPlayer getAtlasPlayer();
	
	public ChatMode getChatMode();

}
