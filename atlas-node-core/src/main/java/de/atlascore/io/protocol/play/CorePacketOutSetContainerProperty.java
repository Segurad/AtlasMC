package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetContainerProperty;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetContainerProperty implements PacketIO<PacketOutSetContainerProperty> {

	@Override
	public void read(PacketOutSetContainerProperty packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.windowID = in.readByte();
		packet.property = in.readShort();
		packet.value = in.readShort();
	}

	@Override
	public void write(PacketOutSetContainerProperty packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.windowID);
		out.writeShort(packet.property);
		out.writeShort(packet.value);
	}
	
	@Override
	public PacketOutSetContainerProperty createPacketData() {
		return new PacketOutSetContainerProperty();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetContainerProperty.class);
	}

}
