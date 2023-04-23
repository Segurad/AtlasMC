package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.EnchantingTable;
import de.atlasmc.chat.Chat;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreEnchantingTable extends CoreTileEntity implements EnchantingTable {
	
	private Chat name;
	
	public CoreEnchantingTable(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
	}

	@Override
	public Inventory getInventory() {
		return ContainerFactory.ENCHANTING_INV_FACTORY.create(InventoryType.ENCHANTING, name, this);
	}

	@Override
	public Chat getCustomName() {
		return name;
	}

	@Override
	public void setCustomName(Chat name) {
		this.name = name;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!systemData) return;
		writer.writeStringTag(NBT_CUSTOM_NAME, name.getJsonText());
	}

}
