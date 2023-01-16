package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInClickWindowButton;
import io.netty.buffer.ByteBuf;

public class CorePacketInClickWindowButton extends PacketIO<PacketInClickWindowButton> {
	
	@Override
	public void read(PacketInClickWindowButton packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setWindowID(in.readByte());
		packet.setButtonID(in.readByte());
	}

	@Override
	public void write(PacketInClickWindowButton packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.getWindowID());
		out.writeByte(packet.getButtonID());
	}
	
	@Override
	public PacketInClickWindowButton createPacketData() {
		return new PacketInClickWindowButton();
	}

}
