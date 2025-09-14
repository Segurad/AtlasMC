package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_ENTITY_VELOCITY, definition = "set_entity_motion")
public class PacketOutSetEntityVelocity extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public double x;
	public double y;
	public double z;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_ENTITY_VELOCITY;
	}

}
