package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketInCloseContainer;
import io.netty.buffer.ByteBuf;

public class CorePacketInCloseContainer implements PacketIO<PacketInCloseContainer> {

	@Override
	public void read(PacketInCloseContainer packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.windowID = in.readByte();
	}

	@Override
	public void write(PacketInCloseContainer packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.windowID);
	}

	@Override
	public PacketInCloseContainer createPacketData() {
		return new PacketInCloseContainer();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInCloseContainer.class);
	}

}
