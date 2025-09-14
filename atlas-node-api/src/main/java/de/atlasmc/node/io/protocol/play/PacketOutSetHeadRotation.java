package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_HEAD_ROTATION, definition = "rotate_head")
public class PacketOutSetHeadRotation extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public float yaw;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_HEAD_ROTATION;
	}

}
