package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SET_RENDER_DISTANCE, definition = "set_chunk_cache_radius")
public class PacketOutRenderDistance extends AbstractPacket implements PacketPlayOut {
	
	public int distance;
	
	@Override
	public int getDefaultID() {
		return OUT_SET_RENDER_DISTANCE;	
	}

}
