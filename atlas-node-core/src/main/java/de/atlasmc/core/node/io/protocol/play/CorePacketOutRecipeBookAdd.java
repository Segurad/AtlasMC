package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.inventory.ItemType;
import de.atlasmc.node.io.protocol.play.PacketOutRecipeBookAdd;
import de.atlasmc.node.io.protocol.play.PacketOutRecipeBookAdd.RecipeData;
import de.atlasmc.node.recipe.RecipeCategory;
import de.atlasmc.node.recipe.display.RecipeDisplay;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.enums.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutRecipeBookAdd implements PacketCodec<PacketOutRecipeBookAdd> {

	private static final class Holder {
		
		private static final StreamCodec<DataSet<ItemType>> SET_CODEC = DataSet.streamCodec(ItemType.REGISTRY_KEY);
		
	}
	
	@Override
	public void deserialize(PacketOutRecipeBookAdd packet, ByteBuf in, ConnectionHandler con) throws IOException {
		final int count = readVarInt(in);
		if (count == 0) {
			packet.recipes = List.of();
			packet.replace = in.readBoolean();
			return;
		}
		final var context = con.getCodecContext();
		List<RecipeData> recipes = new ArrayList<>(count);
		packet.recipes = recipes;
		for (int i = 0; i < count; i++) {
			RecipeData data = new RecipeData();
			recipes.add(data);
			data.id = readVarInt(in);
			data.display = RecipeDisplay.STREAM_CODEC.deserialize(in, context);
			data.groupID = readVarInt(in);
			data.category = EnumUtil.getByID(RecipeCategory.class, readVarInt(in));
			if (in.readBoolean()) {
				final int ingredientCount = readVarInt(in);
				if (ingredientCount > 0) {
					@SuppressWarnings("unchecked")
					DataSet<ItemType>[] ingretients = new DataSet[ingredientCount];
					for (int j = 0; j < ingredientCount; j++) {
						ingretients[j] = Holder.SET_CODEC.deserialize(in, context);
					}
					data.ingredients = ingretients;
				}
			}
			data.flags = in.readByte();
		}
		packet.replace = in.readBoolean();
	}

	@Override
	public void serialize(PacketOutRecipeBookAdd packet, ByteBuf out, ConnectionHandler con) throws IOException {
		List<RecipeData> recipes = packet.recipes;
		if (recipes == null || recipes.isEmpty()) {
			writeVarInt(0, out);
			out.writeBoolean(packet.replace);
			return;
		}
		final var context = con.getCodecContext();
		final int count = recipes.size();
		writeVarInt(count, out);
		for (int i = 0; i < count; i++) {
			RecipeData data = recipes.get(i);
			writeVarInt(data.id, out);
			data.display.writeToStream(out, null);
			writeVarInt(data.category.getID(), out);
			DataSet<ItemType>[] ingredients = data.ingredients;
			if (ingredients == null || ingredients.length == 0) {
				out.writeBoolean(false);
			} else {
				out.writeBoolean(true);
				final int ingredientCount = ingredients.length;
				writeVarInt(ingredientCount, out);
				
				for (var set : ingredients) {
					Holder.SET_CODEC.serialize(set, out, context);
				}
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
