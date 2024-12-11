package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.ItemStack;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutUpdateRecipes;
import de.atlasmc.recipe.AbstractCookingRecipe;
import de.atlasmc.recipe.BlastFurnaceRecipe;
import de.atlasmc.recipe.CampfireRecipe;
import de.atlasmc.recipe.FurnaceRecipe;
import de.atlasmc.recipe.Ingredient;
import de.atlasmc.recipe.ItemIngredient;
import de.atlasmc.recipe.Recipe;
import de.atlasmc.recipe.RecipeCategory;
import de.atlasmc.recipe.RecipeType;
import de.atlasmc.recipe.ShapedRecipe;
import de.atlasmc.recipe.ShapelessRecipe;
import de.atlasmc.recipe.AbstractSmithingRecipe;
import de.atlasmc.recipe.SmithingTransformRecipe;
import de.atlasmc.recipe.SmithingTrimRecipe;
import de.atlasmc.recipe.SmokerRecipe;
import de.atlasmc.recipe.StonecuttingRecipe;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public class CorePacketOutUpdateRecipes implements PacketIO<PacketOutUpdateRecipes> {

	@Override
	public void read(PacketOutUpdateRecipes packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		final int recipeCount = readVarInt(in);
		if (recipeCount == 0)
			return;
		List<Recipe> recipes = new ArrayList<>(recipeCount);
		final NBTNIOReader reader = new NBTNIOReader(in, true);
		for (int i = 0; i < recipeCount; i++) {
			final RecipeType type = RecipeType.getByName(readString(in));
			final NamespacedKey key = readIdentifier(in);
			switch (type) {
			case CRAFTING_SHAPELESS: 
			{
				NamespacedKey group = readIdentifier(in);
				RecipeCategory category = RecipeCategory.getByID(readVarInt(in));
				final int ingredientCount = readVarInt(in);
				List<Ingredient> ingredients = new ArrayList<>(ingredientCount);
				for (int j = 0; j < ingredientCount; j++)
					ingredients.add(readIngredient(in, reader));
				ItemStack result = readSlot(in, reader);
				ShapelessRecipe recipe = new ShapelessRecipe(key, group, category);
				recipe.setIngredients(ingredients);
				recipe.setResult(result);
				recipes.add(recipe);
				break;
			}
			case CRAFTING_SHAPED: {
				final int width = readVarInt(in);
				final int height = readVarInt(in);
				final char[] shapeBuf = new char[width * height];
				NamespacedKey group = readIdentifier(in);
				RecipeCategory category = RecipeCategory.getByID(readVarInt(in));
				// --- read shape and ingredients ---
				// in protocol the ingredients are set in each shape slot
				// the atlas implementation maps the ingredients that are the same to chars
				// so we need to recreate this mappings
				Arrays.fill(shapeBuf, '0');
				char currentMaxID = 'A';
				HashMap<Ingredient, Character> ingredients = new HashMap<>();
				for (int j = 0; j < shapeBuf.length; j++) {
					Ingredient ingredient = readIngredient(in, reader);
					if (ingredient == null)
						continue;
					if (ingredients.containsKey(ingredient))
						shapeBuf[j] = ingredients.get(ingredient);
					else {
						shapeBuf[j] = currentMaxID;
						ingredients.put(ingredient, currentMaxID++);
					}
				}
				// --- build recipe ---
				final ShapedRecipe recipe = new ShapedRecipe(key, group, category);
				final String[] shape = new String[height];
				for (int j = 0, k = 0; j < height; j++, k += width)
					shape[j] = new String(shapeBuf, k, width);
				recipe.setShape(shape);
				ingredients.forEach((ingredient, charKey) -> {
					recipe.setIngredient(charKey, ingredient);
				});
				recipe.setResult(readSlot(in, reader));
				recipe.setShowNotification(in.readBoolean());
				recipes.add(recipe);
				break;
			}
			case SMELTING: {
				NamespacedKey group = readIdentifier(in);
				RecipeCategory category = RecipeCategory.getByID(readVarInt(in)+4);
				recipes.add(readCookingRecipe(new FurnaceRecipe(key, group, category), in, reader));
				break;
			}
			case BLASTING: {
				NamespacedKey group = readIdentifier(in);
				RecipeCategory category = RecipeCategory.getByID(readVarInt(in)+4);
				recipes.add(readCookingRecipe(new BlastFurnaceRecipe(key, group, category), in, reader));
				break;
			}
			case SMOKING: {
				NamespacedKey group = readIdentifier(in);
				RecipeCategory category = RecipeCategory.getByID(readVarInt(in)+4);
				recipes.add(readCookingRecipe(new SmokerRecipe(key, group ,category), in, reader));
				break;
			}
			case CAMPFIRE_COOKING: {
				NamespacedKey group = readIdentifier(in);
				RecipeCategory category = RecipeCategory.getByID(readVarInt(in)+4);
				recipes.add(readCookingRecipe(new CampfireRecipe(key, group, category), in, reader));
				break;
			}
			case SMITHING_TRIM:
			{
				SmithingTrimRecipe recipe = new SmithingTrimRecipe(key);
				recipe.setTemplateIngrent(readIngredient(in, reader));
				recipe.setBaseIngredient(readIngredient(in, reader));
				recipe.setAdditionalIngredient(readIngredient(in, reader));
				recipes.add(recipe);
				break;
			}
			case SMITHING_TRANSFORM:
			{
				SmithingTransformRecipe recipe = new SmithingTransformRecipe(key);
				recipe.setTemplateIngrent(readIngredient(in, reader));
				recipe.setBaseIngredient(readIngredient(in, reader));
				recipe.setAdditionalIngredient(readIngredient(in, reader));
				recipe.setResult(readSlot(in, reader));
				recipes.add(recipe);
				break;
			}
			case STONECUTTING:
			{
				NamespacedKey group = readIdentifier(in);
				StonecuttingRecipe recipe = new StonecuttingRecipe(key, group);
				recipe.setIngredient(readIngredient(in, reader));
				recipe.setResult(readSlot(in, reader));
				recipes.add(recipe);
				break;
			}
			case CRAFTING_SPECIAL_ARMORDYE:
			case CRAFTING_SPECIAL_BOOKCLONING:
			case CRAFTING_SPECIAL_MAPCLONING:
			case CRAFTING_SPECIAL_MAPEXTENDING:
			case CRAFTING_SPECIAL_FIREWORK_ROCKET:
			case CRAFTING_SPECIAL_FIREWORK_STAR:
			case CRAFTING_SPECIAL_FIREWORK_STAR_FADE:
			case CRAFTING_SPECIAL_REPAIR_ITEM:
			case CRAFTING_SPECIAL_TIPPEDARROW:
			case CRAFTING_SPECIAL_BANNERDUPLICATE:
			case CRAFTING_SPECIAL_SHIELDDECORATION:
			case CRAFTING_SPECIAL_SHULKERBOXCOLORING:
			case CRAFTING_SPECIAL_SUSPICIOUSSTEW:
			case CRAFTING_DECORATED_POT: 
			{
				RecipeCategory category = RecipeCategory.getByID(readVarInt(in));
				break;
			}
			default:
				break;
			}
		}
		reader.close();
		packet.recipes = recipes;
	}
	
	protected AbstractCookingRecipe readCookingRecipe(AbstractCookingRecipe recipe, ByteBuf in, NBTNIOReader reader) throws IOException {
		recipe.setIngredient(readIngredient(in, reader));
		recipe.setResult(readSlot(in, reader));
		recipe.setXp(in.readFloat());
		recipe.setTime(readVarInt(in));
		return recipe;
	}
	
	protected Ingredient readIngredient(ByteBuf in, NBTNIOReader reader) throws IOException {
		final int count = readVarInt(in);
		if (count < 1)
			return null;
		List<ItemStack> items = new ArrayList<>(count);
		for (int i = 0; i < count; i++)
			items.add(readSlot(in, reader));
		return new ItemIngredient(items);
	}

	@Override
	public void write(PacketOutUpdateRecipes packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		List<Recipe> recipes = packet.recipes;
		if (recipes == null) {
			writeVarInt(0, out);
			return;
		}
		final int size = recipes.size();
		writeVarInt(size, out);
		if (size == 0)
			return;
		final NBTNIOWriter writer = new NBTNIOWriter(out, true);
		for (int i = 0; i < size; i++) {
			Recipe recipe = recipes.get(i);
			writeString(recipe.getType().getNameID(), out);
			writeString(recipe.getNamespacedKeyRaw(), out);
			switch (recipe.getType()) {
			case CRAFTING_SHAPELESS: {
				ShapelessRecipe shapeless = (ShapelessRecipe) recipe;
				NamespacedKey group = shapeless.getGroup();
				writeString(group != null ? group.toString() : null, out);
				writeVarInt(recipe.getCategory().getID(), out);
				writeVarInt(shapeless.getIngredients().size(), out);
				for (Ingredient ingredient : shapeless.getIngredients()) {
					writeIngredient(ingredient, out, writer);
				}
				writeSlot(shapeless.getResult(), out, writer);
				break;
			}
			case CRAFTING_SHAPED: {
				ShapedRecipe shaped = (ShapedRecipe) recipe;
				writeVarInt(shaped.getShapeWidth(), out);
				writeVarInt(shaped.getShapeHeight(), out);
				NamespacedKey group = shaped.getGroup();
				writeString(group != null ? group.toString() : null, out);
				writeVarInt(recipe.getCategory().getID(), out);
				// --- write ingredients in shape ---
				String[] shape = shaped.getShape();
				for (String line : shape)
					for (int j = 0; j < line.length(); j++)
						writeIngredient(shaped.getIngredient(line.charAt(j)), out, writer);
				// --- write result
				writeSlot(shaped.getResult(), out, writer);
				out.writeBoolean(shaped.getShowNotification());
				break;
			}
			case SMELTING:
			case SMOKING:
			case BLASTING:
			case CAMPFIRE_COOKING: {
				AbstractCookingRecipe cooking = (AbstractCookingRecipe) recipe;
				NamespacedKey group = cooking.getGroup();
				writeString(group != null ? group.toString() : null, out);
				writeVarInt(recipe.getCategory().getID()-4, out);
				writeIngredient(cooking.getIngredient(), out, writer);
				writeSlot(cooking.getResult(), out, writer);
				out.writeFloat(cooking.getXp());
				writeVarInt(cooking.getTime(), out);
				break;
			}
			case SMITHING_TRIM: {
				AbstractSmithingRecipe smithing = (AbstractSmithingRecipe) recipe;
				writeIngredient(smithing.getTemplateIngrent(), out, writer);
				writeIngredient(smithing.getBaseIngredient(), out, writer);
				writeIngredient(smithing.getAdditionalIngredient(), out, writer);
				writeSlot(smithing.getResult(), out);
				break;
			}
			case SMITHING_TRANSFORM:
			{
				AbstractSmithingRecipe smithing = (AbstractSmithingRecipe) recipe;
				writeIngredient(smithing.getTemplateIngrent(), out, writer);
				writeIngredient(smithing.getBaseIngredient(), out, writer);
				writeIngredient(smithing.getAdditionalIngredient(), out, writer);
				break;
			}
			case STONECUTTING: {
				StonecuttingRecipe cutting = (StonecuttingRecipe) recipe;
				NamespacedKey group = cutting.getGroup();
				writeString(group != null ? group.toString() : null, out);
				writeIngredient(cutting.getIngredient(), out, writer);
				writeSlot(cutting.getResult(), out, writer);
				break;
			}
			case CRAFTING_SPECIAL_ARMORDYE:
			case CRAFTING_SPECIAL_BOOKCLONING:
			case CRAFTING_SPECIAL_MAPCLONING:
			case CRAFTING_SPECIAL_MAPEXTENDING:
			case CRAFTING_SPECIAL_FIREWORK_ROCKET:
			case CRAFTING_SPECIAL_FIREWORK_STAR:
			case CRAFTING_SPECIAL_FIREWORK_STAR_FADE:
			case CRAFTING_SPECIAL_REPAIR_ITEM:
			case CRAFTING_SPECIAL_TIPPEDARROW:
			case CRAFTING_SPECIAL_BANNERDUPLICATE:
			case CRAFTING_SPECIAL_SHIELDDECORATION:
			case CRAFTING_SPECIAL_SHULKERBOXCOLORING:
			case CRAFTING_SPECIAL_SUSPICIOUSSTEW:
			case CRAFTING_DECORATED_POT: 
			{
				writeVarInt(recipe.getCategory().getID(), out);
				break;
			}
			default:
				break;
			}
		}
		writer.close();
	}
	
	protected void writeIngredient(Ingredient ingredient, ByteBuf out, NBTNIOWriter writer) throws IOException {
		List<ItemStack> items = ingredient.getUseableItems();
		if (items == null || items.isEmpty()) {
			writeVarInt(0, out);
			return;
		}
		writeVarInt(items.size(), out);
		for (ItemStack item : items)
			writeSlot(item, out, writer);
	}

	@Override
	public PacketOutUpdateRecipes createPacketData() {
		return new PacketOutUpdateRecipes();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutUpdateRecipes.class);
	}

}
