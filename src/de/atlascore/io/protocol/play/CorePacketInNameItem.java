package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketInNameItem;
import io.netty.buffer.ByteBuf;

public class CorePacketInNameItem extends CoreAbstractHandler<PacketInNameItem> {
	
	@Override
	public void read(PacketInNameItem packet, ByteBuf in, ConnectionHandler con) throws IOException {
		 packet.setItemName(readString(in));
	}

	@Override
	public void write(PacketInNameItem packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeString(packet.getItemName(), out);
	}
	
	@Override
	public PacketInNameItem createPacketData() {
		return new PacketInNameItem();
	}

}
