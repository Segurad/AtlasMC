package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutUpdateRecipeBook;
import de.atlasmc.io.protocol.play.PacketOutUpdateRecipeBook.RecipesAction;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateRecipeBook implements PacketIO<PacketOutUpdateRecipeBook> {

	@Override
	public void read(PacketOutUpdateRecipeBook packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setAction(RecipesAction.getByID(readVarInt(in)));
		packet.setCraftingBookOpen(in.readBoolean());
		packet.setCraftingBookFiltered(in.readBoolean());
		packet.setSmeltingBookOpen(in.readBoolean());
		packet.setSmeltingBookFiltered(in.readBoolean());
		packet.setBlastingBookOpen(in.readBoolean());
		packet.setBlastingBookFiltered(in.readBoolean());
		packet.setSmokingBookOpen(in.readBoolean());
		packet.setSmokingBookFiltered(in.readBoolean());
		int size = readVarInt(in);
		List<NamespacedKey> tagged = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			tagged.add(i, readIdentifier(in));
		}
		if (packet.getAction() == RecipesAction.INIT) 
			return;
		size = readVarInt(in);
		List<NamespacedKey> untagged = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			untagged.add(i, readIdentifier(in));
		}
	}

	@Override
	public void write(PacketOutUpdateRecipeBook packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getAction().getID(), out);
		out.writeBoolean(packet.isCraftingBookOpen());
		out.writeBoolean(packet.isCraftingBookFiltered());
		out.writeBoolean(packet.isSmeltingBookOpen());
		out.writeBoolean(packet.isSmeltingBookFiltered());
		out.writeBoolean(packet.isBlastingBookOpen());
		out.writeBoolean(packet.isBlastingBookFiltered());
		out.writeBoolean(packet.isSmokingBookOpen());
		out.writeBoolean(packet.isSmokingBookFilered());
		if (packet.getTagged() == null) {
			writeVarInt(0, out);
		} else {
			writeVarInt(packet.getTagged().size(), out);
			for (NamespacedKey key : packet.getTagged()) {
				writeString(key.toString(), out);
			}
		}
		if (packet.getAction() == RecipesAction.INIT) 
			return;
		if (packet.getUntagged() == null) {
			writeVarInt(0, out);
			return;
		}
		writeVarInt(packet.getUntagged().size(), out);
		for (NamespacedKey key : packet.getUntagged()) {
			writeString(key.toString(), out);
		}
	}

	@Override
	public PacketOutUpdateRecipeBook createPacketData() {
		return new PacketOutUpdateRecipeBook();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateRecipeBook.class);
	}

}
