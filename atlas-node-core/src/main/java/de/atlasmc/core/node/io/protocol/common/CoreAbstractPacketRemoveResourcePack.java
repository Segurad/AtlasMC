package de.atlasmc.core.node.io.protocol.common;

import java.io.IOException;

import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.codec.UUIDCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.common.AbstractPacketRemoveResourcePack;
import io.netty.buffer.ByteBuf;

public abstract class CoreAbstractPacketRemoveResourcePack<T extends AbstractPacketRemoveResourcePack> implements PacketCodec<T> {
	
	@Override
	public void deserialize(T packet, ByteBuf in, ConnectionHandler con) throws IOException {
		if (in.readBoolean())
			packet.uuid = UUIDCodec.readUUID(in);
	}
	
	@Override
	public void serialize(T packet, ByteBuf out, ConnectionHandler con) throws IOException {
		if (packet.uuid == null) {
			out.writeBoolean(false);
		} else {
			out.writeBoolean(true);
			UUIDCodec.writeUUID(packet.uuid, out);
		}
	}

}
