package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutEntityPositionAndRotation;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityPositionAndRotation extends AbstractPacket implements PacketOutEntityPositionAndRotation{

	private int entityID, yaw, pitch;
	private short x, y, z;
	private boolean onGround;
	
	public CorePacketOutEntityPositionAndRotation() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutEntityPositionAndRotation(int entityID, double x, double y, double z, double prevX, double prevY, double prevZ, float yaw, float pitch, boolean onGround) {
		this();
		this.entityID = entityID;
		this.x = MathUtil.delta(x, prevX);
		this.y = MathUtil.delta(y, prevY);
		this.z = MathUtil.delta(z, prevZ);
		this.yaw = MathUtil.toAngle(yaw);
		this.pitch = MathUtil.toAngle(pitch);
		this.onGround = onGround;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		entityID = readVarInt(in);
		x = in.readShort();
		y = in.readShort();
		z = in.readShort();
		yaw = in.readUnsignedByte();
		pitch = in.readUnsignedByte();
		onGround = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entityID, out);
		out.writeShort(x);
		out.writeShort(y);
		out.writeShort(z);
		out.writeByte(yaw);
		out.writeByte(pitch);
		out.writeBoolean(onGround);
	}

	@Override
	public int getEntityID() {
		return entityID;
	}

	@Override
	public short getDeltaX() {
		return x;
	}

	@Override
	public short getDeltaY() {
		return y;
	}

	@Override
	public short getDeltaZ() {
		return z;
	}

	@Override
	public float getPitch() {
		return MathUtil.fromAngle(pitch);
	}

	@Override
	public float getYaw() {
		return MathUtil.fromAngle(yaw);
	}

	@Override
	public boolean isOnGround() {
		return onGround;
	}

	@Override
	public void setEntityID(int id) {
		this.entityID = id;
	}

	@Override
	public void setLocation(short deltaX, short deltaY, short deltaZ, float yaw, float pitch) {
		this.x = deltaX;
		this.y = deltaY;
		this.z = deltaZ;
		this.yaw = MathUtil.toAngle(yaw);
		this.pitch = MathUtil.toAngle(pitch);
	}

	@Override
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

}
