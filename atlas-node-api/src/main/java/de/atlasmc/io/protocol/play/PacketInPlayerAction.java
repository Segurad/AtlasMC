package de.atlasmc.io.protocol.play;

import de.atlasmc.block.BlockFace;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_PLAYER_ACTION, definition = "player_action")
public class PacketInPlayerAction extends AbstractPacket implements PacketPlayIn {
	
	public int status;
	public long position;
	public BlockFace face;
	public int sequence;
	
	@Override
	public int getDefaultID() {
		return IN_PLAYER_ACTION;
	}
	
}
