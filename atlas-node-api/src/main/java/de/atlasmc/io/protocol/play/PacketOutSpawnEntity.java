package de.atlasmc.io.protocol.play;

import java.util.UUID;

import org.joml.Vector3d;
import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.FallingBlock;
import de.atlasmc.entity.Hanging;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SPAWN_ENTITY)
public class PacketOutSpawnEntity extends AbstractPacket implements PacketPlayOut {
	
	private int entityID;
	private EntityType type;
	private int objectdata;
	private UUID uuid;
	private double x, y, z;
	private float yaw, pitch;
	private float headYaw;
	private double velocityX, velocityY, velocityZ;
	
	public int getEntityID() {
		return entityID;
	}

	public EntityType getType() {
		return type;
	}

	public int getObjectdata() {
		return objectdata;
	}

	public UUID getUUID() {
		return uuid;
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

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public float getHeadYaw() {
		return headYaw;
	}
	
	public double getVelocityX() {
		return velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public double getVelocityZ() {
		return velocityZ;
	}

	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

	public void setObjectdata(int objectdata) {
		this.objectdata = objectdata;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
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

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public void setHeadYaw(float headYaw) {
		this.headYaw = headYaw;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	public void setVelocityZ(double velocityZ) {
		this.velocityZ = velocityZ;
	}

	public void setLocation(double x, double y, double z, float pitch, float yaw) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public void setVelocity(double x, double y, double z) {
		this.velocityX = x;
		this.velocityY = y;
		this.velocityZ = z;
	}
	
	public void setEntity(Entity entity) {
		entityID = entity.getID();
		type = entity.getType();
		uuid = entity.getUUID();
		x = entity.getX();
		y = entity.getY();
		z = entity.getZ();
		yaw = entity.getYaw();
		pitch = entity.getPitch();
		objectdata = 0;
		/*if (entity instanceof Projectile) {
			Ignored
		} else*/ if (entity instanceof Hanging hanging) {
			BlockFace ori = hanging.getAttachedFace();
			switch (ori) {
			case DOWN: objectdata = 0; break;
			case UP: objectdata = 1; break;
			case NORTH: objectdata = 2; break;
			case SOUTH: objectdata = 3; break;
			case WEST: objectdata = 4; break;
			case EAST: objectdata = 5; break;
			default:
				break;
			}
		} else if (entity instanceof LivingEntity livingEnt) {
			// TODO headyaw
		} else if (entity instanceof FallingBlock) {
			objectdata = ((FallingBlock) entity).getBlockData().getStateID();
		} /*else if (entity instanceof FishingHook) {
			Ignored
		}*/
		if (entity.hasVelocity()) {
			Vector3d v = entity.getVelocity();
			setVelocity(v.x, v.y, v.z);
		}
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SPAWN_ENTITY;
	}
	
}
