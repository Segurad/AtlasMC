package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInSetRecipeBookState;
import de.atlasmc.recipe.BookType;
import io.netty.buffer.ByteBuf;

public class CorePacketInSetRecipeBookState extends PacketIO<PacketInSetRecipeBookState> {
	
	@Override
	public void read(PacketInSetRecipeBookState packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setBookType(BookType.getByID(readVarInt(in)));
		packet.setBookOpen(in.readBoolean());
		packet.setFilterActive(in.readBoolean());
	}

	@Override
	public void write(PacketInSetRecipeBookState packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getBookType().getID(), out);
		out.writeBoolean(packet.isBookOpen());
		out.writeBoolean(packet.isFilterActive());
	}
	
	@Override
	public PacketInSetRecipeBookState createPacketData() {
		return new PacketInSetRecipeBookState();
	}

}
