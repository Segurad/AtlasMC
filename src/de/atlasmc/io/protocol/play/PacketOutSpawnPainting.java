package de.atlasmc.io.protocol.play;

import java.util.UUID;

import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.Painting.Motive;
import de.atlasmc.io.Packet;

public interface PacketOutSpawnPainting extends Packet {
	
	public int getEntityID();
	public UUID getUUID();
	public Motive getMotiv();
	public long getPosition();
	public BlockFace getDirection();
	
	@Override
	default int getDefaultID() {
		return 0x03;
	}

}
