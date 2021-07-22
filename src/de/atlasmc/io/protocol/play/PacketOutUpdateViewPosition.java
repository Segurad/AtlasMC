package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

/**
 * This packet is send whenever a player crosses a chunk border
 */
@DefaultPacketID(PacketPlay.OUT_UPDATE_VIEW_POSITION)
public interface PacketOutUpdateViewPosition extends PacketPlay, PacketOutbound {
	
	public int getChunkX();
	public int getChunkZ();
	
	@Override
	public default int getDefaultID() {
		return OUT_UPDATE_VIEW_POSITION;
	}

}
