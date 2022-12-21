package de.atlasmc.io.protocol.play;

import java.util.UUID;

import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.Painting;
import de.atlasmc.entity.Painting.Motive;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.util.MathUtil;

@DefaultPacketID(PacketPlay.OUT_SPAWN_PAINTING)
public class PacketOutSpawnPainting extends AbstractPacket implements PacketPlayOut {
	
	private int entityID;
	private Motive motiv;
	private BlockFace direction;
	private UUID uuid;
	private long position;

	public int getEntityID() {
		return entityID;
	}

	public Motive getMotiv() {
		return motiv;
	}

	public BlockFace getDirection() {
		return direction;
	}

	public UUID getUUID() {
		return uuid;
	}

	public long getPosition() {
		return position;
	}

	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}

	public void setMotiv(Motive motiv) {
		this.motiv = motiv;
	}

	public void setDirection(BlockFace direction) {
		this.direction = direction;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public void setPosition(long position) {
		this.position = position;
	}

	public void setEntity(Painting painting) {
		entityID = painting.getID();
		uuid = painting.getUUID();
		position = MathUtil.toPosition(painting.getX(), painting.getY(), painting.getZ());
		motiv = painting.getMotive();
		direction = painting.getAttachedFace();
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SPAWN_PAINTING;
	}

}
