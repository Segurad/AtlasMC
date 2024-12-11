package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutUpdateRecipeBook;
import de.atlasmc.io.protocol.play.PacketOutUpdateRecipeBook.RecipesAction;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateRecipeBook implements PacketIO<PacketOutUpdateRecipeBook> {

	@Override
	public void read(PacketOutUpdateRecipeBook packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.action = RecipesAction.getByID(readVarInt(in));
		packet.craftingOpen = in.readBoolean();
		packet.craftingFilter = in.readBoolean();
		packet.smeltingOpen = in.readBoolean();
		packet.smeltingFilter = in.readBoolean();
		packet.blastFurnaceOpen = in.readBoolean();
		packet.blastFurnaceFilter = in.readBoolean();
		packet.smokerOpen = in.readBoolean();
		packet.smokerFilter = in.readBoolean();
		int size = readVarInt(in);
		List<NamespacedKey> tagged = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			tagged.add(i, readIdentifier(in));
		}
		if (packet.action == RecipesAction.INIT) 
			return;
		size = readVarInt(in);
		List<NamespacedKey> untagged = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			untagged.add(i, readIdentifier(in));
		}
	}

	@Override
	public void write(PacketOutUpdateRecipeBook packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.action.getID(), out);
		out.writeBoolean(packet.craftingOpen);
		out.writeBoolean(packet.craftingFilter);
		out.writeBoolean(packet.smeltingOpen);
		out.writeBoolean(packet.smeltingFilter);
		out.writeBoolean(packet.blastFurnaceOpen);
		out.writeBoolean(packet.blastFurnaceFilter);
		out.writeBoolean(packet.smokerOpen);
		out.writeBoolean(packet.smokerFilter);
		if (packet.tagged == null) {
			writeVarInt(0, out);
		} else {
			final int size = packet.tagged.size();
			writeVarInt(size, out);
			for (int i = 0; i < size; i++) {
				NamespacedKey key = packet.tagged.get(i);
				writeString(key.toString(), out);
			}
		}
		if (packet.action == RecipesAction.INIT) 
			return;
		if (packet.untagged == null) {
			writeVarInt(0, out);
		} else {
			final int size = packet.untagged.size();
			writeVarInt(size, out);
			for (int i = 0; i < size; i++) {
				NamespacedKey key = packet.untagged.get(i);
				writeString(key.toString(), out);
			}
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
