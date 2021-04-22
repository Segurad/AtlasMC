package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutEntityPosition;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityPosition extends AbstractPacket implements PacketOutEntityPosition {

	private int entityID;
	private short x, y, z;
	private boolean onGround;
	
	public CorePacketOutEntityPosition() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutEntityPosition(int entityID, double x, double y, double z, double prevX, double prevY, double prevZ, boolean onGround) {
		this();
		this.entityID = entityID;
		this.x = MathUtil.delta(x, prevX);
		this.y = MathUtil.delta(y, prevY);
		this.z = MathUtil.delta(z, prevZ);
		this.onGround = onGround;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		entityID = readVarInt(in);
		x = in.readShort();
		y = in.readShort();
		z = in.readShort();
		onGround = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entityID, out);
		out.writeShort(x);
		out.writeShort(y);
		out.writeShort(z);
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
	public boolean isOnGround() {
		return onGround;
	}

}
