package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInSelectTrade;
import io.netty.buffer.ByteBuf;

public class CorePacketInSelectTrade implements PacketIO<PacketInSelectTrade> {

	@Override
	public void read(PacketInSelectTrade packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setSelectedSlot(readVarInt(in));
	}

	@Override
	public void write(PacketInSelectTrade packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getSelectedSlot(), out);
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
