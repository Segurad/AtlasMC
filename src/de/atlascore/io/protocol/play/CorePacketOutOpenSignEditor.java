package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutOpenSignEditor;
import io.netty.buffer.ByteBuf;

public class CorePacketOutOpenSignEditor extends CoreAbstractHandler<PacketOutOpenSignEditor> {
	
	@Override
	public void read(PacketOutOpenSignEditor packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPosition(in.readLong());
	}

	@Override
	public void write(PacketOutOpenSignEditor packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getPosition());
	}

	@Override
	public PacketOutOpenSignEditor createPacketData() {
		return new PacketOutOpenSignEditor();
	}

}
