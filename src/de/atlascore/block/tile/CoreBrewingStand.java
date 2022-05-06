package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.BrewingStand;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.BrewingInventory;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreBrewingStand extends CoreAbstractContainerTile<BrewingInventory> implements BrewingStand {
	
	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	BREW_TIME = CharKey.of("BrewTime"),
	FUEL = CharKey.of("Fuel"),
	ITEMS = CharKey.of("Items");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreAbstractContainerTile.NBT_FIELDS);
		NBT_FIELDS.setField(BREW_TIME, (holder, reader) -> {
			((BrewingStand) holder).getInventory().setBrewTime(reader.readIntTag());
		});
		NBT_FIELDS.setField(FUEL, (holder, reader) -> {
			((BrewingStand) holder).getInventory().setFuelLevel(reader.readByteTag());
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
