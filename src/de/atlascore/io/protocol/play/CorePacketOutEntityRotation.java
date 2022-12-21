package de.atlascore.io.protocol.play;

import static de.atlasmc.io.AbstractPacket.readVarInt;
import static de.atlasmc.io.AbstractPacket.writeVarInt;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutEntityRotation;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityRotation extends CoreAbstractHandler<PacketOutEntityRotation> {

	@Override
	public void read(PacketOutEntityRotation packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setYaw(MathUtil.fromAngle(in.readUnsignedByte()));
		packet.setPitch(MathUtil.fromAngle(in.readUnsignedByte()));
		packet.setOnGround(in.readBoolean());
	}

	@Override
	public void write(PacketOutEntityRotation packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		out.writeByte(MathUtil.toAngle(packet.getYaw()));
		out.writeByte(MathUtil.toAngle(packet.getPitch()));
		out.writeBoolean(packet.isOnGround());
	}

	@Override
	public PacketOutEntityRotation createPacketData() {
		return new PacketOutEntityRotation();
	}

}
