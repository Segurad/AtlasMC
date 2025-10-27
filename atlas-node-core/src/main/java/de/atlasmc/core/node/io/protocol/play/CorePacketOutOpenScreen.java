package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.io.protocol.play.PacketOutOpenScreen;
import de.atlasmc.util.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutOpenScreen implements PacketIO<PacketOutOpenScreen> {

	@Override
	public void read(PacketOutOpenScreen packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.windowID = readVarInt(in);
		packet.type = EnumUtil.getByID(InventoryType.class, readVarInt(in));
		packet.title = Chat.STREAM_CODEC.deserialize(in, handler.getCodecContext());
	}

	@Override
	public void write(PacketOutOpenScreen packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.windowID, out);
		writeVarInt(packet.type.getID(), out);
		Chat.STREAM_CODEC.serialize(packet.title, out, handler.getCodecContext());
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
