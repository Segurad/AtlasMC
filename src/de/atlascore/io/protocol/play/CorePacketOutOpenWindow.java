package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.event.inventory.InventoryType;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutOpenWindow;
import io.netty.buffer.ByteBuf;

public class CorePacketOutOpenWindow extends CoreAbstractHandler<PacketOutOpenWindow> {

	@Override
	public void read(PacketOutOpenWindow packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setWindowID(readVarInt(in));
		packet.setType(InventoryType.getByID(readVarInt(in)));
		packet.setTitle(readString(in));
	}

	@Override
	public void write(PacketOutOpenWindow packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getWindowID(), out);
		writeVarInt(packet.getType().getID(), out);
		writeString(packet.getTitle(), out);
	}
	
	@Override
	public PacketOutOpenWindow createPacketData() {
		return new PacketOutOpenWindow();
	}

}
