package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketInPickItem;
import io.netty.buffer.ByteBuf;

public class CorePacketInPickItem extends CoreAbstractHandler<PacketInPickItem> {
	
	@Override
	public void read(PacketInPickItem packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setSlotToUse(readVarInt(in));
	}

	@Override
	public void write(PacketInPickItem packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.getSlotToUse(), out);
	}

	@Override
	public PacketInPickItem createPacketData() {
		return new PacketInPickItem();
	}
	
}
