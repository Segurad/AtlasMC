package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInChangeRecipeBookSettings;
import de.atlasmc.node.recipe.BookType;
import de.atlasmc.util.enums.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketInChangeRecipeBookSettings implements PacketCodec<PacketInChangeRecipeBookSettings> {
	
	@Override
	public void deserialize(PacketInChangeRecipeBookSettings packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.bookType = EnumUtil.getByID(BookType.class, readVarInt(in));
		packet.bookOpen = in.readBoolean();
		packet.filterActive =in.readBoolean();
	}

	@Override
	public void serialize(PacketInChangeRecipeBookSettings packet, ByteBuf out, ConnectionHandler handler) throws IOException {
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
