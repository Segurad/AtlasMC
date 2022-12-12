package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketInCreativeInventoryAction;
import io.netty.buffer.ByteBuf;

public class CorePacketInCreativeInventoryAction extends CoreAbstractHandler<PacketInCreativeInventoryAction> {
	
	@Override
	public void read(PacketInCreativeInventoryAction packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setSlot(in.readShort());
		packet.setClickedItem(readSlot(in));
	}

	@Override
	public void write(PacketInCreativeInventoryAction packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeShort(packet.getSlot());
		writeSlot(packet.getClickedItem(), out);
	}
	
	@Override
	public PacketInCreativeInventoryAction createPacketData() {
		return new PacketInCreativeInventoryAction();
	}

}
