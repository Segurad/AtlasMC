package de.atlascore.io.protocol.play;

import static de.atlasmc.io.AbstractPacket.readVarInt;
import static de.atlasmc.io.AbstractPacket.writeVarInt;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutUpdateEntityPositionAndRotation;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityUpdatePositionAndRotation implements PacketIO<PacketOutUpdateEntityPositionAndRotation> {
	
	@Override
	public void read(PacketOutUpdateEntityPositionAndRotation packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setDeltaX(in.readShort());
		packet.setDeltaY(in.readShort());
		packet.setDeltaZ(in.readShort());
		packet.setYaw(MathUtil.fromAngle(in.readUnsignedByte()));
		packet.setPitch(MathUtil.fromAngle(in.readUnsignedByte()));
		packet.setOnGround(in.readBoolean());
	}

	@Override
	public void write(PacketOutUpdateEntityPositionAndRotation packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		out.writeShort(packet.getDeltaX());
		out.writeShort(packet.getDeltaY());
		out.writeShort(packet.getDeltaZ());
		out.writeByte(MathUtil.toAngle(packet.getYaw()));
		out.writeByte(MathUtil.toAngle(packet.getPitch()));
		out.writeBoolean(packet.isOnGround());
	}

	@Override
	public PacketOutUpdateEntityPositionAndRotation createPacketData() {
		return new PacketOutUpdateEntityPositionAndRotation();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateEntityPositionAndRotation.class);
	}

}
