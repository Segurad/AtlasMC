package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.UUID;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Location;
import de.atlasmc.entity.HumanEntity;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutSpawnPlayer;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSpawnPlayer extends AbstractPacket implements PacketOutSpawnPlayer {

	private int id, pitch, yaw;
	private double x, y, z;
	private UUID uuid;
	
	public CorePacketOutSpawnPlayer() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutSpawnPlayer(HumanEntity player) {
		this();
		uuid = player.getUUID();
		id = player.getID();
		Location loc = player.getLocation();
		x = loc.getX();
		y = loc.getY();
		z = loc.getZ();
		pitch = MathUtil.toAngle(loc.getPitch());
		yaw = MathUtil.toAngle(loc.getYaw());
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		id = readVarInt(in);
		long most = in.readLong();
		long least = in.readLong();
		uuid = new UUID(most, least);
		x = in.readDouble();
		y = in.readDouble();
		z = in.readDouble();
		yaw = in.readUnsignedByte();
		pitch = in.readUnsignedByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(id, out);
		out.writeLong(uuid.getMostSignificantBits());
		out.writeLong(uuid.getLeastSignificantBits());
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(z);
		out.writeByte(yaw);
		out.writeByte(pitch);
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

}
