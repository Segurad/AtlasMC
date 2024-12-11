package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_CONTAINER_PROPERTY, definition = "container_set_data")
public class PacketOutSetContainerProperty extends AbstractPacket implements PacketPlayOut {

	public int windowID;
	public int property;
	public int value;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_CONTAINER_PROPERTY;
	}
	
}
