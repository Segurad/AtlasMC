package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutDeclareRecipes;
import de.atlasmc.recipe.AbstractCookingRecipe;
import de.atlasmc.recipe.BlastFurnaceRecipe;
import de.atlasmc.recipe.CampfireRecipe;
import de.atlasmc.recipe.FurnaceRecipe;
import de.atlasmc.recipe.Ingredient;
import de.atlasmc.recipe.ItemIngredient;
import de.atlasmc.recipe.Recipe;
import de.atlasmc.recipe.RecipeType;
import de.atlasmc.recipe.ShapedRecipe;
import de.atlasmc.recipe.ShapelessRecipe;
import de.atlasmc.recipe.SmithingRecipe;
import de.atlasmc.recipe.SmokerRecipe;
import de.atlasmc.recipe.StonecuttingRecipe;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import de.atlasmc.util.nbt.io.NBTNIOWriter;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDeclareRecipes extends AbstractPacket implements PacketOutDeclareRecipes {

	private List<Recipe> recipes;
	
	public CorePacketOutDeclareRecipes() {
		super(CoreProtocolAdapter.VERSION);
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		final int recipeCount = readVarInt(in);
		if (recipeCount == 0)
			return;
		recipes = new ArrayList<>(recipeCount);
		final NBTNIOReader reader = new NBTNIOReader(in);
		for (int i = 0; i < recipeCount; i++) {
			final RecipeType type = RecipeType.getByName(readString(in));
			final NamespacedKey key = new NamespacedKey(readString(in));
			switch (type) {
			case CRAFTING_SHAPELESS: 
			{
				readString(in); // read group
				final int ingredientCount = readVarInt(in);
				List<Ingredient> ingredients = new ArrayList<>(ingredientCount);
				for (int j = 0; j < ingredientCount; j++)
					ingredients.add(readIngredient(in, reader));
				ItemStack result = readSlot(in, reader);
				ShapelessRecipe recipe = new ShapelessRecipe(key);
				recipe.setIngredients(ingredients);
				recipe.setResult(result);
				recipes.add(recipe);
			}
				break;
			case CRAFTING_SHAPED:
			{
				final int width = readVarInt(in);
				final int height = readVarInt(in);
				final char[] shapeBuf = new char[width * height];
				readString(in); // read group
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
				final ShapedRecipe recipe = new ShapedRecipe(key);
				final String[] shape = new String[height];
				for (int j = 0, k = 0; j < height; j++, k += width)
					shape[j] = new String(shapeBuf, k, width);
				recipe.setShape(shape);
				ingredients.forEach((ingredient, charKey) -> {
					recipe.setIngredient(charKey, ingredient);
				});
				recipe.setResult(readSlot(in, reader));
				recipes.add(recipe);
			}
				break;
			case SMELTING:
				recipes.add(readCookingRecipe(new FurnaceRecipe(key), in, reader));
				break;
			case BLASTING:
				recipes.add(readCookingRecipe(new BlastFurnaceRecipe(key), in, reader));
				break;
			case SMOKING:
				recipes.add(readCookingRecipe(new SmokerRecipe(key), in, reader));
				break;
			case CAMPFIRE_COOKING:
				recipes.add(readCookingRecipe(new CampfireRecipe(key), in, reader));
				break;
			case SMITHING:
			{
				SmithingRecipe recipe = new SmithingRecipe(key);
				recipe.setBaseIngredient(readIngredient(in, reader));
				recipe.setAdditionalIngredient(readIngredient(in, reader));
				recipe.setResult(readSlot(in, reader));
				recipes.add(recipe);
			}
				break;
			case STONECUTTING:
			{
				readString(in); // read group
				StonecuttingRecipe recipe = new StonecuttingRecipe(key);
				recipe.setIngredient(readIngredient(in, reader));
				recipe.setResult(readSlot(in, reader));
				recipes.add(recipe);
			}
				break;
			default:
				break;
			}
		}
		reader.close();
	}
	
	protected AbstractCookingRecipe readCookingRecipe(AbstractCookingRecipe recipe, ByteBuf in, NBTNIOReader reader) throws IOException {
		readString(in); // read group
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
	public void write(ByteBuf out) throws IOException {
		if (recipes == null) {
			writeVarInt(0, out);
			return;
		}
		writeVarInt(recipes.size(), out);
		final NBTNIOWriter writer = new NBTNIOWriter(out);
		for (Recipe recipe : recipes) {
			writeString(recipe.getType().getNameID(), out);
			writeString(recipe.getNamespacedName(), out);
			switch (recipe.getType()) {
			case CRAFTING_SHAPELESS:
				ShapelessRecipe shapeless = (ShapelessRecipe) recipe;
				writeString(null, out); // TODO write group
				writeVarInt(shapeless.getIngredients().size(), out);
				for (Ingredient ingredient : shapeless.getIngredients()) {
					writeIngredient(ingredient, out, writer);
				}
				writeSlot(shapeless.getResult(), out, writer);
				break;
			case CRAFTING_SHAPED:
				ShapedRecipe shaped = (ShapedRecipe) recipe;
				writeVarInt(shaped.getShapeWidth(), out);
				writeVarInt(shaped.getShapeHeight(), out);
				writeString(null, out); // TODO write group
				// --- write ingredients in shape ---
				String[] shape = shaped.getShape();
				for (String line : shape)
					for (int i = 0; i < line.length(); i++)
						writeIngredient(shaped.getIngredient(line.charAt(i)), out, writer);
				// --- write result
				writeSlot(shaped.getResult(), out, writer);
				break;
			case SMELTING:
			case SMOKING:
			case BLASTING:
			case CAMPFIRE_COOKING:
				AbstractCookingRecipe cooking = (AbstractCookingRecipe) recipe;
				writeString(null, out); // TODO write group
				writeIngredient(cooking.getIngredient(), out, writer);
				writeSlot(cooking.getResult(), out, writer);
				out.writeFloat(cooking.getXp());
				writeVarInt(cooking.getTime(), out);
				break;
			case SMITHING:
				SmithingRecipe smithing = (SmithingRecipe) recipe;
				writeIngredient(smithing.getBaseIngredient(), out, writer);
				writeIngredient(smithing.getAdditionalIngredient(), out, writer);
				writeSlot(smithing.getResult(), out);
				break;
			case STONECUTTING:
				StonecuttingRecipe cutting = (StonecuttingRecipe) recipe;
				writeString(null, out); // TODO write group
				writeIngredient(cutting.getIngredient(), out, writer);
				writeSlot(cutting.getResult(), out, writer);
				break;
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
	public List<Recipe> getRecipes() {
		return recipes;
	}

	@Override
	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

}
