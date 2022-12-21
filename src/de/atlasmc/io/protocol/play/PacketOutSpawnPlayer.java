package de.atlasmc.io.protocol.play;

import java.util.UUID;

import de.atlasmc.entity.HumanEntity;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SPAWN_PLAYER)
public class PacketOutSpawnPlayer extends AbstractPacket implements PacketPlayOut {
	
	private int entityID;
	private float pitch, yaw;
	private double x, y, z;
	private UUID uuid;
	
	public int getEntityID() {
		return entityID;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public UUID getUUID() {
		return uuid;
	}

	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public void setEntity(HumanEntity entity) {
		uuid = entity.getUUID();
		entityID = entity.getID();
		x = entity.getX();
		y = entity.getY();
		z = entity.getZ();
		pitch = entity.getPitch();
		yaw = entity.getYaw();
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SPAWN_PLAYER;
	}

}
