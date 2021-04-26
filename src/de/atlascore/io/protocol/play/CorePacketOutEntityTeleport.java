package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutEntityTeleport;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityTeleport extends AbstractPacket implements PacketOutEntityTeleport {

	private int entityID, yaw, pitch;
	private double x, y, z;
	private boolean onGround;
	
	public CorePacketOutEntityTeleport() { 
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutEntityTeleport(int entityID, double x, double y, double z, float yaw, float pitch, boolean onGround) {
		this();
		this.entityID = entityID;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = MathUtil.toAngle(yaw);
		this.pitch = MathUtil.toAngle(pitch);
		this.onGround = onGround;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		entityID = readVarInt(in);
		x = in.readDouble();
		y = in.readDouble();
		z = in.readDouble();
		yaw = in.readUnsignedByte();
		pitch = in.readUnsignedByte();
		onGround = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entityID, out);
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(z);
		out.writeByte(yaw);
		out.writeByte(pitch);
		out.writeBoolean(onGround);
	}

	@Override
	public int getEntityID() {
		return entityID;
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
	public boolean isOnGround() {
		return onGround;
	}

}
