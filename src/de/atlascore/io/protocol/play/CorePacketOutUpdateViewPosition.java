package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutUpdateViewPosition;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateViewPosition extends CoreAbstractHandler<PacketOutUpdateViewPosition> {

	@Override
	public void read(PacketOutUpdateViewPosition packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setChunkX(readVarInt(in));
		packet.setChunkZ(readVarInt(in));
	}

	@Override
	public void write(PacketOutUpdateViewPosition packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getChunkX(), out);
		writeVarInt(packet.getChunkZ(), out);
	}

	@Override
	public PacketOutUpdateViewPosition createPacketData() {
		return new PacketOutUpdateViewPosition();
	}

}
