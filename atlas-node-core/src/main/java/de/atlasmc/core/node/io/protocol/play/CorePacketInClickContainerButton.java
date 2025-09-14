package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketInClickContainerButton;
import io.netty.buffer.ByteBuf;

public class CorePacketInClickContainerButton implements PacketIO<PacketInClickContainerButton> {
	
	@Override
	public void read(PacketInClickContainerButton packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.windowID = in.readByte();
		packet.buttonID = in.readByte();
	}

	@Override
	public void write(PacketInClickContainerButton packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.windowID);
		out.writeByte(packet.buttonID);
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
