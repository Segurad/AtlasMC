package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutEntityHeadLook;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityHeadLook extends AbstractPacket implements PacketOutEntityHeadLook {

	private int entityID, yaw;
	
	public CorePacketOutEntityHeadLook() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutEntityHeadLook(int entityID, float yaw) {
		this();
		this.entityID = entityID;
		this.yaw = MathUtil.toAngle(yaw);
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		entityID = readVarInt(in);
		yaw = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entityID, out);
		out.writeByte(yaw);
	}

	@Override
	public int getEntityID() {
		return entityID;
	}

	@Override
	public float getYaw() {
		return MathUtil.fromAngle(yaw);
	}

}
