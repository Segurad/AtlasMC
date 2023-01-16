package de.atlascore.io.protocol.play;

import static de.atlasmc.io.AbstractPacket.readVarInt;
import static de.atlasmc.io.AbstractPacket.writeVarInt;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutEntityPositionAndRotation;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityPositionAndRotation extends PacketIO<PacketOutEntityPositionAndRotation> {
	
	@Override
	public void read(PacketOutEntityPositionAndRotation packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setDeltaX(in.readShort());
		packet.setDeltaY(in.readShort());
		packet.setDeltaZ(in.readShort());
		packet.setYaw(MathUtil.fromAngle(in.readUnsignedByte()));
		packet.setPitch(MathUtil.fromAngle(in.readUnsignedByte()));
		packet.setOnGround(in.readBoolean());
	}

	@Override
	public void write(PacketOutEntityPositionAndRotation packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		out.writeShort(packet.getDeltaX());
		out.writeShort(packet.getDeltaY());
		out.writeShort(packet.getDeltaZ());
		out.writeByte(MathUtil.toAngle(packet.getYaw()));
		out.writeByte(MathUtil.toAngle(packet.getPitch()));
		out.writeBoolean(packet.isOnGround());
	}

	@Override
	public PacketOutEntityPositionAndRotation createPacketData() {
		return new PacketOutEntityPositionAndRotation();
	}

}
