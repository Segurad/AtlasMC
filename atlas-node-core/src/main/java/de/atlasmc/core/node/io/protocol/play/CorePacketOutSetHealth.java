package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSetHealth;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetHealth implements PacketIO<PacketOutSetHealth> {

	@Override
	public void read(PacketOutSetHealth packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.health = in.readFloat();
		packet.food = readVarInt(in);
		packet.saturation = in.readFloat();
	}

	@Override
	public void write(PacketOutSetHealth packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeFloat(packet.health);
		writeVarInt(packet.food, out);
		out.writeFloat(packet.saturation);
	}

	@Override
	public PacketOutSetHealth createPacketData() {
		return new PacketOutSetHealth();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetHealth.class);
	}

}
