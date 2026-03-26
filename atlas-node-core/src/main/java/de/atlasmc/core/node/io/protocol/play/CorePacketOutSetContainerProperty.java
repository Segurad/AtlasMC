package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSetContainerProperty;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetContainerProperty implements PacketCodec<PacketOutSetContainerProperty> {

	@Override
	public void deserialize(PacketOutSetContainerProperty packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.windowID = in.readByte();
		packet.property = in.readShort();
		packet.value = in.readShort();
	}

	@Override
	public void serialize(PacketOutSetContainerProperty packet, ByteBuf out, ConnectionHandler handler) throws IOException {
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
