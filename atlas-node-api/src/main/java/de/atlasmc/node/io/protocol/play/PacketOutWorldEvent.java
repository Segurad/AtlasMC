package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.world.WorldEvent;

@DefaultPacketID(packetID = PacketPlay.OUT_WORLD_EVENT, definition = "level_event")
public class PacketOutWorldEvent extends AbstractPacket implements PacketPlayOut {
	
	public WorldEvent event;
	public long position;
	public int data;
	public boolean disableRelativeVolume;
	
	@Override
	public int getDefaultID() {
		return OUT_WORLD_EVENT;
	}

}
