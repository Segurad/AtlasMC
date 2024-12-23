package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetCursorItem;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CorePacketOutSetCursorItem implements PacketIO<PacketOutSetCursorItem> {

	@Override
	public void read(PacketOutSetCursorItem packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.item = readSlot(in);
	}

	@Override
	public void write(PacketOutSetCursorItem packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeSlot(packet.item, out);
	}

	@Override
	public PacketOutSetCursorItem createPacketData() {
		return new PacketOutSetCursorItem();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetCursorItem.class);
	}

}
