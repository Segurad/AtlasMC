package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInRenameItem;
import io.netty.buffer.ByteBuf;

public class CorePacketInRenameItem implements PacketIO<PacketInRenameItem> {
	
	@Override
	public void read(PacketInRenameItem packet, ByteBuf in, ConnectionHandler con) throws IOException {
		 packet.setItemName(readString(in, 32767));
	}

	@Override
	public void write(PacketInRenameItem packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeString(packet.getItemName(), out);
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
