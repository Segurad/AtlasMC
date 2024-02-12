package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutCloseContainer;
import io.netty.buffer.ByteBuf;

public class CorePacketOutCloseContainer implements PacketIO<PacketOutCloseContainer> {

	@Override
	public void read(PacketOutCloseContainer packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setWindowID(in.readUnsignedByte());
	}

	@Override
	public void write(PacketOutCloseContainer packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getWindowID());
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
