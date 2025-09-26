package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutRecipeBookAdd;
import de.atlasmc.node.io.protocol.play.PacketOutRecipeBookAdd.RecipeData;
import de.atlasmc.node.recipe.Ingredient;
import de.atlasmc.node.recipe.RecipeCategory;
import io.netty.buffer.ByteBuf;

public class CorePacketOutRecipeBookAdd implements PacketIO<PacketOutRecipeBookAdd> {

	@Override
	public void read(PacketOutRecipeBookAdd packet, ByteBuf in, ConnectionHandler con) throws IOException {
		final int count = readVarInt(in);
		if (count == 0) {
			packet.recipes = List.of();
			packet.replace = in.readBoolean();
			return;
		}
		List<RecipeData> recipes = new ArrayList<>(count);
		packet.recipes = recipes;
		for (int i = 0; i < count; i++) {
			RecipeData data = new RecipeData();
			recipes.add(data);
			data.id = readVarInt(in);
			// TODO read display
			data.groupID = readVarInt(in);
			data.category = RecipeCategory.getByID(readVarInt(in));
			if (in.readBoolean()) {
				final int ingredientCount = readVarInt(in);
				for (int j = 0; j < ingredientCount; j++) {
					// TODO read ingredient
				}
			}
			data.flags = in.readByte();
		}
		packet.replace = in.readBoolean();
	}

	@Override
	public void write(PacketOutRecipeBookAdd packet, ByteBuf out, ConnectionHandler con) throws IOException {
		List<RecipeData> recipes = packet.recipes;
		if (recipes == null || recipes.isEmpty()) {
			writeVarInt(0, out);
			out.writeBoolean(packet.replace);
			return;
		}
		final int count = recipes.size();
		writeVarInt(count, out);
		for (int i = 0; i < count; i++) {
			RecipeData data = recipes.get(i);
			writeVarInt(data.id, out);
			// TODO write display
			writeVarInt(data.category.getID(), out);
			List<Ingredient> ingredients = data.ingredients;
			if (ingredients == null || ingredients.isEmpty()) {
				out.writeBoolean(false);
			} else {
				out.writeBoolean(true);
				final int ingredientCount = ingredients.size();
				writeVarInt(ingredientCount, out);
				// TODO write ingredient
			}
			out.writeByte(data.flags);
		}
		out.writeBoolean(packet.replace);
	}

	@Override
	public PacketOutRecipeBookAdd createPacketData() {
		return new PacketOutRecipeBookAdd();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutRecipeBookAdd.class);
	}

}
