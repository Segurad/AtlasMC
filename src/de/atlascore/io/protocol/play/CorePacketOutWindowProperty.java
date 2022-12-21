package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutWindowProperty;
import io.netty.buffer.ByteBuf;

public class CorePacketOutWindowProperty extends CoreAbstractHandler<PacketOutWindowProperty> {

	@Override
	public void read(PacketOutWindowProperty packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setWindowID(in.readByte());
		packet.setProperty(in.readShort());
		packet.setValue(in.readShort());
	}

	@Override
	public void write(PacketOutWindowProperty packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getWindowID());
		out.writeShort(packet.getProperty());
		out.writeShort(packet.getValue());
	}
	
	@Override
	public PacketOutWindowProperty createPacketData() {
		return new PacketOutWindowProperty();
	}

}
