package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutPlayerPositionAndLook;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPlayerPositionAndLook extends AbstractPacket implements PacketOutPlayerPositionAndLook {

	private double x, y, z;
	private float yaw, pitch;
	private int flags, teleportID;
	
	public CorePacketOutPlayerPositionAndLook() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutPlayerPositionAndLook(double x, double y, double z, float yaw, float pitch, int flags, int teleportID) {
		this();
		this.x = x;
		this.y = y;
		this.yaw = yaw;
		this.pitch = pitch;
		this.flags = flags;
		this.teleportID = teleportID;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		x = in.readDouble();
		y = in.readDouble();
		z = in.readDouble();
		yaw = in.readFloat();
		pitch = in.readFloat();
		flags = in.readByte();
		teleportID = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(z);
		out.writeFloat(yaw);
		out.writeFloat(pitch);
		out.writeByte(flags);
		writeVarInt(teleportID, out);
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
	public int getFlags() {
		return flags;
	}

	@Override
	public int getTeleportID() {
		return teleportID;
	}

}
