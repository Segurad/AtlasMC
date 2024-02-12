package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInClickContainerButton;
import io.netty.buffer.ByteBuf;

public class CorePacketInClickContainerButton implements PacketIO<PacketInClickContainerButton> {
	
	@Override
	public void read(PacketInClickContainerButton packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setWindowID(in.readByte());
		packet.setButtonID(in.readByte());
	}

	@Override
	public void write(PacketInClickContainerButton packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.getWindowID());
		out.writeByte(packet.getButtonID());
	}
	
	@Override
	public PacketInClickContainerButton createPacketData() {
		return new PacketInClickContainerButton();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInClickContainerButton.class);
	}

}
