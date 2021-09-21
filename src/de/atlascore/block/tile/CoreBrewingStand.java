package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.BrewingStand;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.BrewingInventory;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreBrewingStand extends CoreAbstractContainerTile<BrewingInventory> implements BrewingStand {
	
	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final String
	BREW_TIME = "BrewTime",
	FUEL = "Fuel",
	ITEMS = "Items";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreAbstractContainerTile.NBT_FIELDS);
		NBT_FIELDS.setField(BREW_TIME, (holder, reader) -> {
			if (holder instanceof BrewingStand)
			((BrewingStand) holder).getInventory().setBrewTime(reader.readIntTag());
			else reader.skipTag();
		});
		NBT_FIELDS.setField(FUEL, (holder, reader) -> {
			if (holder instanceof BrewingStand)
			((BrewingStand) holder).getInventory().setFuelLevel(reader.readByteTag());
			else reader.skipTag();
		});
	}
	
	public CoreBrewingStand(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
	}

	@Override
	protected BrewingInventory createInventory() {
		return ContainerFactory.BREWING_INV_FACTORY.create(InventoryType.BREWING, this);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!systemData) return;
		writer.writeIntTag(BREW_TIME, getInventory().getBrewTime());
		writer.writeByteTag(FUEL, getInventory().getFuelLevel());
	}

}
