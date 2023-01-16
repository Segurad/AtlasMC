package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutEntityMovement;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityMovement extends PacketIO<PacketOutEntityMovement> {

	@Override
	public void read(PacketOutEntityMovement packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
	}

	@Override
	public void write(PacketOutEntityMovement packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
	}

	@Override
	public PacketOutEntityMovement createPacketData() {
		return new PacketOutEntityMovement();
	}

}
