package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetHeadRotation;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetHeadRotation implements PacketIO<PacketOutSetHeadRotation> {

	@Override
	public void read(PacketOutSetHeadRotation packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setYaw(MathUtil.fromAngle(in.readByte()));
	}

	@Override
	public void write(PacketOutSetHeadRotation packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		out.writeByte(MathUtil.toAngle(packet.getYaw()));
	}
	
	@Override
	public PacketOutSetHeadRotation createPacketData() {
		return new PacketOutSetHeadRotation();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetHeadRotation.class);
	}

}
