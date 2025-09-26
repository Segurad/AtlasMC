package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInRenameItem;
import io.netty.buffer.ByteBuf;

public class CorePacketInRenameItem implements PacketIO<PacketInRenameItem> {
	
	@Override
	public void read(PacketInRenameItem packet, ByteBuf in, ConnectionHandler con) throws IOException {
		 packet.itemName = readString(in, 32767);
	}

	@Override
	public void write(PacketInRenameItem packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeString(packet.itemName, out);
	}
	
	@Override
	public PacketInRenameItem createPacketData() {
		return new PacketInRenameItem();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInRenameItem.class);
	}

}
