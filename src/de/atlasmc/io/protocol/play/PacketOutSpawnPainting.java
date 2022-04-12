package de.atlasmc.io.protocol.play;

import java.util.UUID;

import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.Painting;
import de.atlasmc.entity.Painting.Motive;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_SPAWN_PAINTING)
public interface PacketOutSpawnPainting extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	
	public UUID getUUID();
	
	public Motive getMotiv();
	
	public long getPosition();
	
	public BlockFace getDirection();
	
	public void setEntity(Painting entity);
	
	@Override
	default int getDefaultID() {
		return OUT_SPAWN_PAINTING;
	}

}
