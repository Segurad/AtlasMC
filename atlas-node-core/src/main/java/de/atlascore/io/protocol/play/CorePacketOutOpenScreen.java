package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.inventory.InventoryType;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutOpenScreen;
import io.netty.buffer.ByteBuf;

public class CorePacketOutOpenScreen implements PacketIO<PacketOutOpenScreen> {

	@Override
	public void read(PacketOutOpenScreen packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.windowID = readVarInt(in);
		packet.type = InventoryType.getByID(readVarInt(in));
		packet.title = readTextComponent(in);
	}

	@Override
	public void write(PacketOutOpenScreen packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.windowID, out);
		writeVarInt(packet.type.getID(), out);
		writeTextComponent(packet.title, out);
	}
	
	@Override
	public PacketOutOpenScreen createPacketData() {
		return new PacketOutOpenScreen();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutOpenScreen.class);
	}

}
