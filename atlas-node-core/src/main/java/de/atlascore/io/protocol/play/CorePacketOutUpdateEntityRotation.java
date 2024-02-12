package de.atlascore.io.protocol.play;

import static de.atlasmc.io.AbstractPacket.readVarInt;
import static de.atlasmc.io.AbstractPacket.writeVarInt;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutUpdateEntityRotation;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateEntityRotation implements PacketIO<PacketOutUpdateEntityRotation> {

	@Override
	public void read(PacketOutUpdateEntityRotation packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setYaw(MathUtil.fromAngle(in.readUnsignedByte()));
		packet.setPitch(MathUtil.fromAngle(in.readUnsignedByte()));
		packet.setOnGround(in.readBoolean());
	}

	@Override
	public void write(PacketOutUpdateEntityRotation packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		out.writeByte(MathUtil.toAngle(packet.getYaw()));
		out.writeByte(MathUtil.toAngle(packet.getPitch()));
		out.writeBoolean(packet.isOnGround());
	}

	@Override
	public PacketOutUpdateEntityRotation createPacketData() {
		return new PacketOutUpdateEntityRotation();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateEntityRotation.class);
	}

}
