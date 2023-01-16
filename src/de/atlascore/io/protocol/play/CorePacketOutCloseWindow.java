package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutCloseWindow;
import io.netty.buffer.ByteBuf;

public class CorePacketOutCloseWindow extends PacketIO<PacketOutCloseWindow> {

	@Override
	public void read(PacketOutCloseWindow packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setWindowID(in.readByte());
	}

	@Override
	public void write(PacketOutCloseWindow packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getWindowID());
	}
	
	@Override
	public PacketOutCloseWindow createPacketData() {
		return new PacketOutCloseWindow();
	}

}
