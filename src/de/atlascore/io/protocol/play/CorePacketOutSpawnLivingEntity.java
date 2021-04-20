package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.UUID;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Location;
import de.atlasmc.Vector;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutSpawnLivingEntity;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSpawnLivingEntity extends AbstractPacket implements PacketOutSpawnLivingEntity {

	private int id, type;
	private UUID uuid;
	private double x, y, z;
	private float yaw, pitch, headpitch;
	private short vx, vy, vz;
	
	public CorePacketOutSpawnLivingEntity() {
		super(0x02, CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutSpawnLivingEntity(LivingEntity entity) {
		this();
		id = entity.getID();
		uuid = entity.getUUID();
		type = entity.getType().getTypeID();
		Location loc = entity.getLocation();
		x = loc.getX();
		y = loc.getY();
		z = loc.getZ();
		yaw = loc.getYaw();
		pitch = loc.getPitch();
		headpitch = entity.getHeadPitch();
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
		yaw = MathUtil.fromAngle(in.readByte());
		pitch = MathUtil.fromAngle(in.readByte());
		headpitch = MathUtil.fromAngle(in.readByte());
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
		out.writeByte(MathUtil.toAngle(yaw));
		out.writeByte(MathUtil.toAngle(pitch));
		out.writeByte(MathUtil.toAngle(headpitch));
		out.writeShort(vx);
		out.writeShort(vy);
		out.writeShort(vz);
	}

	@Override
	public int getEntityID() {
		return id;
	}

	@Override
	public int getType() {
		return type;
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
		return yaw;
	}

	@Override
	public float getPitch() {
		return pitch;
	}

	@Override
	public float getHeadPitch() {
		return headpitch;
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

}
