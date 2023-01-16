package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInSelectTrade;
import io.netty.buffer.ByteBuf;

public class CorePacketInSelectTrade extends PacketIO<PacketInSelectTrade> {

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
	
}
