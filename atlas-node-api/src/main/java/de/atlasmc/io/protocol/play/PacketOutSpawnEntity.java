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

@DefaultPacketID(packetID = PacketPlay.OUT_SPAWN_ENTITY, definition = "add_entity")
public class PacketOutSpawnEntity extends AbstractPacket implements PacketPlayOut {
	
	public int entityID;
	public UUID uuid;
	public EntityType type;
	public double x; 
	public double y;
	public double z;
	public float pitch;
	public float yaw;
	public float headYaw;
	public int objectdata;
	public double velocityX;
	public double velocityY;
	public double velocityZ;

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
			velocityX = v.x;
			velocityY = v.y;
			velocityZ = v.z;
		}
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SPAWN_ENTITY;
	}
	
}
