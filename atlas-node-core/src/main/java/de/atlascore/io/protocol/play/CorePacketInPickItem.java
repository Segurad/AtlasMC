package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInPickItem;
import io.netty.buffer.ByteBuf;

public class CorePacketInPickItem implements PacketIO<PacketInPickItem> {
	
	@Override
	public void read(PacketInPickItem packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.slotToUse = readVarInt(in);
	}

	@Override
	public void write(PacketInPickItem packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.slotToUse, out);
	}

	@Override
	public PacketInPickItem createPacketData() {
		return new PacketInPickItem();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPickItem.class);
	}
	
}
