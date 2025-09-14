package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_DEFAULT_SPAWN_POSITION, definition = "set_default_spawn_position")
public class PacketOutSetDefaultSpawnPosition extends AbstractPacket implements PacketPlayOut {
	
	public long position;
	public float angel;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_DEFAULT_SPAWN_POSITION;
	}

}
