package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketInChangeRecipeBookSettings;
import de.atlasmc.node.recipe.BookType;
import io.netty.buffer.ByteBuf;

public class CorePacketInChangeRecipeBookSettings implements PacketIO<PacketInChangeRecipeBookSettings> {
	
	@Override
	public void read(PacketInChangeRecipeBookSettings packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.bookType = BookType.getByID(readVarInt(in));
		packet.bookOpen = in.readBoolean();
		packet.filterActive =in.readBoolean();
	}

	@Override
	public void write(PacketInChangeRecipeBookSettings packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.bookType.getID(), out);
		out.writeBoolean(packet.bookOpen);
		out.writeBoolean(packet.filterActive);
	}
	
	@Override
	public PacketInChangeRecipeBookSettings createPacketData() {
		return new PacketInChangeRecipeBookSettings();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInChangeRecipeBookSettings.class);
	}

}
