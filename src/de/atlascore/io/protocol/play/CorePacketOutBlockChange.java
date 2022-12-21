package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutBlockChange;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBlockChange extends CoreAbstractHandler<PacketOutBlockChange> {
	
	@Override
	public void read(PacketOutBlockChange packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPosition(in.readLong());
		packet.setBlockStateID(readVarInt(in));
	}

	@Override
	public void write(PacketOutBlockChange packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getPosition());
		writeVarInt(packet.getBlockStateID(), out);
	}

	@Override
	public PacketOutBlockChange createPacketData() {
		return new PacketOutBlockChange();
	}

}
