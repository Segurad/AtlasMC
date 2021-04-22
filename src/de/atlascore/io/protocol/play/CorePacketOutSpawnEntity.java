package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.UUID;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Location;
import de.atlasmc.Vector;
import de.atlasmc.entity.Entity;
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
		id = entity.getID();
		type = entity.getType().getTypeID();
		uuid = entity.getUUID();
		Location loc = entity.getLocation();
		x = loc.getX();
		y = loc.getY();
		z = loc.getZ();
		yaw = MathUtil.toAngle(loc.getYaw());
		pitch = MathUtil.toAngle(loc.getPitch());
		objectdata = entity.getObjectData();
		if (entity.hasVelocity()) {
			Vector v = entity.getVelocity();
			vx = (short) (MathUtil.getInRange(v.getX(), -3.9, 3.9)*8000);
			vy = (short) (MathUtil.getInRange(v.getY(), -3.9, 3.9)*8000);
			vz = (short) (MathUtil.getInRange(v.getZ(), -3.9, 3.9)*8000);
		}
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

}
