package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutRecipeBookRemove;
import io.netty.buffer.ByteBuf;

public class CorePacketOutRecipeBookRemove implements PacketIO<PacketOutRecipeBookRemove> {

	@Override
	public void read(PacketOutRecipeBookRemove packet, ByteBuf in, ConnectionHandler con) throws IOException {
		final int count = readVarInt(in);
		if (count == 0)
			return;
		int[] recipes = new int[count];
		for (int i = 0; i < count; i++) {
			recipes[i] = readVarInt(in);
		}
		packet.recipes = recipes;
	}

	@Override
	public void write(PacketOutRecipeBookRemove packet, ByteBuf out, ConnectionHandler con) throws IOException {
		int[] recipes = packet.recipes;
		if (recipes == null || recipes.length == 0) {
			writeVarInt(0, out);
			return;
		}
		writeVarInt(recipes.length, out);
		for (int i : recipes)
			writeVarInt(i, out);
	}

	@Override
	public PacketOutRecipeBookRemove createPacketData() {
		return new PacketOutRecipeBookRemove();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutRecipeBookRemove.class);
	}

}
