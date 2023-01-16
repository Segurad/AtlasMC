package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInQueryEntityNBT;
import io.netty.buffer.ByteBuf;

public class CorePacketInQueryEntityNBT extends PacketIO<PacketInQueryEntityNBT> {

	@Override
	public void read(PacketInQueryEntityNBT packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setTransactionID(readVarInt(in));
		packet.setEntityID(readVarInt(in));
	}

	@Override
	public void write(PacketInQueryEntityNBT packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getTransactionID(), out);
		writeVarInt(packet.getEntityID(), out);
	}
	
	@Override
	public PacketInQueryEntityNBT createPacketData() {
		return new PacketInQueryEntityNBT();
	}

}
