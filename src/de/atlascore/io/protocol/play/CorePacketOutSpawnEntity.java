package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.UUID;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Vector;
import de.atlasmc.block.BlockFace;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.FallingBlock;
import de.atlasmc.entity.ItemFrame;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutSpawnEntity;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSpawnEntity extends AbstractPacket implements PacketOutSpawnEntity {

	private int id, type, objectdata, yaw, pitch;
	private UUID uuid;
	private double x, y, z;
	private short vx, vy, vz;
	
	public CorePacketOutSpawnEntity() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutSpawnEntity(Entity entity) {
		this();
		setEntity(entity);
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		id = readVarInt(in);
		long most = in.readLong();
		long least = in.readLong();
		uuid = new UUID(most, least);
		type = readVarInt(in);
		x = in.readDouble();
		y = in.readDouble();
		z = in.readDouble();
		yaw = in.readUnsignedByte();
		pitch = in.readUnsignedByte();
		objectdata = in.readInt();
		vx = in.readShort();
		vy = in.readShort();
		vz = in.readShort();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(id, out);
		out.writeLong(uuid.getMostSignificantBits());
		out.writeLong(uuid.getLeastSignificantBits());
		writeVarInt(type, out);
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(z);
		out.writeByte(yaw);
		out.writeByte(pitch);
		out.writeInt(objectdata);
		out.writeShort(vx);
		out.writeShort(vy);
		out.writeShort(vz);
	}

	@Override
	public int getEntityID() {
		return id;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getZ() {
		return z;
	}

	@Override
	public float getYaw() {
		return MathUtil.fromAngle(yaw);
	}

	@Override
	public float getPitch() {
		return MathUtil.fromAngle(pitch);
	}

	@Override
	public double getVelocityX() {
		return vx/8000;
	}

	@Override
	public double getVelocityY() {
		return vy/8000;
	}

	@Override
	public double getVelocityZ() {
		return vz/8000;
	}

	@Override
	public int getObjectData() {
		return objectdata;
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public void setLocation(double x, double y, double z, float pitch, float yaw) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = MathUtil.toAngle(pitch);
		this.yaw = MathUtil.toAngle(yaw);
	}

	@Override
	public void setVelocity(double x, double y, double z) {
		this.x = (short) (x*8000);
		this.y = (short) (y*8000);
		this.z = (short) (z*8000);
	}

	@Override
	public void setEntity(Entity entity) {
		id = entity.getID();
		type = entity.getType().getTypeID();
		uuid = entity.getUUID();
		x = entity.getX();
		y = entity.getY();
		z = entity.getZ();
		yaw = MathUtil.toAngle(entity.getYaw());
		pitch = MathUtil.toAngle(entity.getPitch());
		objectdata = 0;
		/*if (entity instanceof Projectile) {
			Ignored
		} else*/ if (entity instanceof ItemFrame) {
			BlockFace ori = ((ItemFrame) entity).getOrientation();
			switch (ori) {
			case UP: objectdata = 1; break;
			case NORTH: objectdata = 2; break;
			case SOUTH: objectdata = 3; break;
			case WEST: objectdata = 4; break;
			case EAST: objectdata = 5; break;
			default:
				break;
			}
		} else if (entity instanceof FallingBlock) {
			objectdata = ((FallingBlock) entity).getBlockData().getStateID();
		} /*else if (entity instanceof FishingHook) {
			Ignored
		}*/
		if (entity.hasVelocity()) {
			Vector v = entity.getVelocity();
			setVelocity(v.getX(), v.getY(), v.getZ());
		}
	}

}
