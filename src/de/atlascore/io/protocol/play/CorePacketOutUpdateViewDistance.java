package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutUpdateViewDistance;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateViewDistance extends PacketIO<PacketOutUpdateViewDistance> {

	@Override
	public void read(PacketOutUpdateViewDistance packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setDistance(readVarInt(in));
	}

	@Override
	public void write(PacketOutUpdateViewDistance packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getDistance(), out);
	}

	@Override
	public PacketOutUpdateViewDistance createPacketData() {
		return new PacketOutUpdateViewDistance();
	}

}
