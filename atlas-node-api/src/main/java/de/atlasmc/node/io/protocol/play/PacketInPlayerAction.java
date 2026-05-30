package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.block.BlockFace;

@DefaultPacketID(packetID = PacketPlay.IN_PLAYER_ACTION, definition = "player_action")
public class PacketInPlayerAction extends AbstractPacket implements PacketPlayIn {
	
	public static final int
	STATUS_START_DIGGING = 0,
	STATUS_CANCEL_DIGGING = 1,
	STATUS_FINISH_DIGGING = 2,
	STATUS_DROP_ITEM_STACK = 3,
	STATUS_DROP_ITEM = 4,
	/**
	 * Used to indicate that a "right" click action was performed with the current item.
	 * e.g. pull bow, eat food, use bucket
	 */
	STATUS_UPDATE_ITEM = 5,
	STATUS_SWAP_ITEM_IN_HAND = 6;
	
	public int status;
	public long position;
	public BlockFace face;
	public int sequence;
	
	@Override
	public int getDefaultID() {
		return IN_PLAYER_ACTION;
	}
	
}
