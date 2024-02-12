package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutDisconnect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDisconnect implements PacketIO<PacketOutDisconnect> {

	@Override
	public void read(PacketOutDisconnect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setReason(readString(in));
	}

	@Override
	public void write(PacketOutDisconnect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getReason(), out);
	}

	@Override
	public PacketOutDisconnect createPacketData() {
		return new PacketOutDisconnect();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutDisconnect.class);
	}

}
