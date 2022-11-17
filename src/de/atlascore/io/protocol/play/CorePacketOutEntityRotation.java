package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutEntityRotation;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityRotation extends AbstractPacket implements PacketOutEntityRotation {

	private int entityID, yaw, pitch;
	private boolean onGround;
	
	public CorePacketOutEntityRotation() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutEntityRotation(int entityID, float yaw, float pitch, boolean onGround) {
		this();
		this.entityID = entityID;
		this.yaw = MathUtil.toAngle(yaw);
		this.pitch = MathUtil.toAngle(pitch);
		this.onGround = onGround;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		entityID = readVarInt(in);
		yaw = in.readUnsignedByte();
		pitch = in.readUnsignedByte();
		onGround = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entityID, out);
		out.writeByte(yaw);
		out.writeByte(pitch);
		out.writeBoolean(onGround);
	}

	@Override
	public int getEntityID() {
		return entityID;
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
	public boolean isOnGround() {
		return onGround;
	}

	@Override
	public void setEntityID(int id) {
		this.entityID = id;
	}

	@Override
	public void setYaw(float yaw) {
		this.yaw = MathUtil.toAngle(yaw);
	}

	@Override
	public void setPitch(float pitch) {
		this.pitch = MathUtil.toAngle(pitch);
	}

	@Override
	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

}
