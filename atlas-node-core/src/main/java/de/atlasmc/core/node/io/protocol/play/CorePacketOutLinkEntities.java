package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutLinkEntities;
import io.netty.buffer.ByteBuf;

public class CorePacketOutLinkEntities implements PacketCodec<PacketOutLinkEntities> {

	@Override
	public void deserialize(PacketOutLinkEntities packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.attachedEntityID = in.readInt();
		packet.holderEntityID = in.readInt();
	}

	@Override
	public void serialize(PacketOutLinkEntities packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeInt(packet.attachedEntityID);
		out.writeInt(packet.holderEntityID);
	}

	@Override
	public PacketOutLinkEntities createPacketData() {
		return new PacketOutLinkEntities();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutLinkEntities.class);
	}

}
