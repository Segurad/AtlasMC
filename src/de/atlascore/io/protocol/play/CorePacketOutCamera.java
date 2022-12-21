package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutCamera;
import io.netty.buffer.ByteBuf;

public class CorePacketOutCamera extends CoreAbstractHandler<PacketOutCamera> {

	@Override
	public void read(PacketOutCamera packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
	}

	@Override
	public void write(PacketOutCamera packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
	}

	@Override
	public PacketOutCamera createPacketData() {
		return new PacketOutCamera();
	}

}
