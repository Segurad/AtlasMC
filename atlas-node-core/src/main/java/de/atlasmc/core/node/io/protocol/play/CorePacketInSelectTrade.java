package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInSelectTrade;
import io.netty.buffer.ByteBuf;

public class CorePacketInSelectTrade implements PacketIO<PacketInSelectTrade> {

	@Override
	public void read(PacketInSelectTrade packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.selectedSlot = readVarInt(in);
	}

	@Override
	public void write(PacketInSelectTrade packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.selectedSlot, out);
	}

	@Override
	public PacketInSelectTrade createPacketData() {
		return new PacketInSelectTrade();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInSelectTrade.class);
	}
	
}
