package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.BrewingStand;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.BrewingInventory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBrewingStand extends CoreAbstractContainerTile<BrewingInventory> implements BrewingStand {
	
	protected static final ChildNBTFieldContainer<CoreBrewingStand> NBT_FIELDS;
	
	protected static final CharKey
	BREW_TIME = CharKey.literal("BrewTime"),
	FUEL = CharKey.literal("Fuel"),
	ITEMS = CharKey.literal("Items");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreAbstractContainerTile.NBT_FIELDS);
		NBT_FIELDS.setField(BREW_TIME, (holder, reader) -> {
			holder.getInventory().setBrewTime(reader.readIntTag());
		});
		NBT_FIELDS.setField(FUEL, (holder, reader) -> {
			holder.getInventory().setFuelLevel(reader.readByteTag());
		});
	}
	
	public CoreBrewingStand(Material type) {
		super(type);
	}

	@Override
	protected BrewingInventory createInventory() {
		return ContainerFactory.BREWING_INV_FACTORY.create(InventoryType.BREWING, this);
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreBrewingStand> getFieldContainerRoot() {
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
