package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.Furnace;
import de.atlasmc.inventory.AbstractFurnaceInventory;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreFurnace extends CoreAbstractContainerTile<AbstractFurnaceInventory> implements Furnace {

	protected static final NBTFieldSet<CoreFurnace> NBT_FIELDS;
	
	protected static final CharKey
	BURN_TIME = CharKey.literal("BurnTime"),
	COOK_TIME = CharKey.literal("CookTime"),
	COOK_TIME_TOTAL = CharKey.literal("CookTimeTotal"),
	// TODO handle furnace recipes nbt
	RECIPE_USED_SIZE = CharKey.literal("RecipesUsedSize"); // Number of recipes with the same result crafted last in this furnace
	// RECIPE_LOCATION_[INDEX] // Recipes which have the same result and were used last
	// RECIPE_AMOUNT_[INDEX] // Number of end products created by a recipe
	
	static {
		NBT_FIELDS = CoreAbstractContainerTile.NBT_FIELDS.fork();
		NBT_FIELDS.setField(BURN_TIME, (holder, reader) -> {
			holder.getInventory().setFuelLevel(reader.readShortTag());
		});
		NBT_FIELDS.setField(COOK_TIME, (holder, reader) -> {
			holder.getInventory().setProgress(reader.readShortTag());
		});
		NBT_FIELDS.setField(COOK_TIME_TOTAL, (holder, reader) -> {
			holder.getInventory().setMaxProgress(reader.readShortTag());
		});
		NBT_FIELDS.setField(RECIPE_USED_SIZE, NBTField.skip());
	}
	
	public CoreFurnace(BlockType type) {
		super(type);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreFurnace> getFieldSetRoot() {
		return NBT_FIELDS;
	}

	@Override
	protected AbstractFurnaceInventory createInventory() {
		if (getType().getNamespacedKey().equals(BlockType.FURNACE))
			return ContainerFactory.FURNACE_INV_FACTPRY.create(InventoryType.FURNACE, this);
		if (getType().getNamespacedKey().equals(BlockType.BLAST_FURNACE))
			return ContainerFactory.BLAST_FURNACE_INV_FACTORY.create(InventoryType.BLAST_FURNACE, this);
		if (getType().getNamespacedKey().equals(BlockType.SMOKER))
			return ContainerFactory.SMOKER_INV_FACTORY.create(InventoryType.SMOKER, this);
		return null;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!systemData) return;
		if (!hasInventory()) return;
		AbstractFurnaceInventory inv = getInventory();
		writer.writeShortTag(BURN_TIME, inv.getFuelLevel());
		writer.writeShortTag(COOK_TIME, inv.getProgress());
		writer.writeShortTag(COOK_TIME_TOTAL, inv.getMaxProgress());
	}

}
