package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutAttachEntity;
import io.netty.buffer.ByteBuf;

public class CorePacketOutAttachEntity extends CoreAbstractHandler<PacketOutAttachEntity> {

	@Override
	public void read(PacketOutAttachEntity packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setAttachedEntityID(in.readInt());
		packet.setHolderEntityID(in.readInt());
	}

	@Override
	public void write(PacketOutAttachEntity packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeInt(packet.getAttachedEntityID());
		out.writeInt(packet.getHolderEntityID());
	}

	@Override
	public PacketOutAttachEntity createPacketData() {
		return new PacketOutAttachEntity();
	}

}
