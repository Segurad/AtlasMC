package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Furnace;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.AbstractFurnaceInventory;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreFurnace extends CoreAbstractContainerTile<AbstractFurnaceInventory> implements Furnace {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final String
	BURN_TIME = "BurnTime",
	COOK_TIME = "CookTime",
	COOK_TIME_TOTAL = "CookTimeTotal",
	// TODO handle furnace recipes nbt
	RECIPE_USED_SIZE = "RecipesUsedSize"; // Number of recipes with the same result crafted last in this furnace
	// RECIPE_LOCATION_[INDEX] // Recipes which have the same result and were used last
	// RECIPE_AMOUNT_[INDEX] // Number of end products created by a recipe
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreAbstractContainerTile.NBT_FIELDS);
		NBT_FIELDS.setField(BURN_TIME, (holder, reader) -> {
			if (holder instanceof Furnace)
			((Furnace) holder).getInventory().setFuelLevel(reader.readShortTag());
			else reader.skipTag();
		});
		NBT_FIELDS.setField(COOK_TIME, (holder, reader) -> {
			if (holder instanceof Furnace)
			((Furnace) holder).getInventory().setProgress(reader.readShortTag());
			else reader.skipTag();
		});
		NBT_FIELDS.setField(COOK_TIME_TOTAL, (holder, reader) -> {
			if (holder instanceof Furnace)
			((Furnace) holder).getInventory().setMaxProgress(reader.readShortTag());
			else reader.skipTag();
		});
		NBT_FIELDS.setField(RECIPE_USED_SIZE, NBTField.SKIP);
	}
	
	public CoreFurnace(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
	}

	@Override
	protected AbstractFurnaceInventory createInventory() {
		if (getType() == Material.FURNACE)
			return ContainerFactory.FURNACE_INV_FACTPRY.create(InventoryType.FURNACE, this);
		if (getType() == Material.BLAST_FURNACE)
			return ContainerFactory.BLAST_FURNACE_INV_FACTORY.create(InventoryType.BLAST_FURNACE, this);
		if (getType() == Material.SMOKER)
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
