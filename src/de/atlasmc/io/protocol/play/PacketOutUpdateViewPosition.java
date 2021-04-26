package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

/**
 * This packet is send whenever a player crosses a chunk border
 */
public interface PacketOutUpdateViewPosition extends Packet {
	
	public int getChunkX();
	public int getChunkZ();
	
	@Override
	public default int getDefaultID() {
		return 0x40;
	}

}
