package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.NamespacedKey;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutUnlockRecipes;
import de.atlasmc.io.protocol.play.PacketOutUnlockRecipes.RecipesAction;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUnlockRecipes extends CoreAbstractHandler<PacketOutUnlockRecipes> {

	@Override
	public void read(PacketOutUnlockRecipes packet, ByteBuf in, ConnectionHandler handler) throws IOException {
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
			tagged.add(i, new NamespacedKey(readString(in)));
		}
		if (packet.getAction() == RecipesAction.INIT) 
			return;
		size = readVarInt(in);
		List<NamespacedKey> untagged = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			untagged.add(i, new NamespacedKey(readString(in)));
		}
	}

	@Override
	public void write(PacketOutUnlockRecipes packet, ByteBuf out, ConnectionHandler handler) throws IOException {
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
	public PacketOutUnlockRecipes createPacketData() {
		return new PacketOutUnlockRecipes();
	}

}
