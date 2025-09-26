package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutCloseContainer;
import io.netty.buffer.ByteBuf;

public class CorePacketOutCloseContainer implements PacketIO<PacketOutCloseContainer> {

	@Override
	public void read(PacketOutCloseContainer packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.windowID = in.readUnsignedByte();
	}

	@Override
	public void write(PacketOutCloseContainer packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.windowID);
	}
	
	@Override
	public PacketOutCloseContainer createPacketData() {
		return new PacketOutCloseContainer();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutCloseContainer.class);
	}

}
