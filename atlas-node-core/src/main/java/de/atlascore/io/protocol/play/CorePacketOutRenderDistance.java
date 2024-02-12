package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutRenderDistance;
import io.netty.buffer.ByteBuf;

public class CorePacketOutRenderDistance implements PacketIO<PacketOutRenderDistance> {

	@Override
	public void read(PacketOutRenderDistance packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setDistance(readVarInt(in));
	}

	@Override
	public void write(PacketOutRenderDistance packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getDistance(), out);
	}

	@Override
	public PacketOutRenderDistance createPacketData() {
		return new PacketOutRenderDistance();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutRenderDistance.class);
	}

}
