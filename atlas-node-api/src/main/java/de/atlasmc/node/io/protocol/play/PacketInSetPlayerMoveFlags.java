package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_SET_PLAYER_MOVE_FLAGS, definition = "move_player_status_only")
public class PacketInSetPlayerMoveFlags extends AbstractPacket implements PacketPlayIn {
	
	/**
	 * <ul>
	 * <li>0x01 = on ground</li>
	 * <li>0x02 = push against wall</li>
	 * </ul>
	 */
	public int flags;
	
	@Override
	public int getDefaultID() {
		return IN_SET_PLAYER_MOVE_FLAGS;
	}

}
