package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetHealth;
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
