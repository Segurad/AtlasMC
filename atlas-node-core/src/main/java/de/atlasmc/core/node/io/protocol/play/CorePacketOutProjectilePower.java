package de.atlasmc.core.node.io.protocol.play;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutProjectilePower;
import io.netty.buffer.ByteBuf;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;

public class CorePacketOutProjectilePower implements PacketIO<PacketOutProjectilePower> {

	@Override
	public void read(PacketOutProjectilePower packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.entityID = readVarInt(in);
		packet.power = in.readDouble();
	}

	@Override
	public void write(PacketOutProjectilePower packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.entityID, out);
		out.writeDouble(packet.power);
	}

	@Override
	public PacketOutProjectilePower createPacketData() {
		return new PacketOutProjectilePower();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutProjectilePower.class);
	}

}
