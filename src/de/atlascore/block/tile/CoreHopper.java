package de.atlascore.block.tile;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Hopper;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.world.Chunk;

public class CoreHopper extends CoreAbstractContainerTile<Inventory> implements Hopper {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final String
	TRANSFER_COOLDOWN = "TransferCooldown",
	TRANSFER_AMOUNT = "TransferAmount";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreAbstractContainerTile.NBT_FIELDS);
		NBT_FIELDS.setField(TRANSFER_COOLDOWN, (holder, reader) -> {
			if (holder instanceof Hopper)
			((Hopper) holder).setTransferCooldown(reader.readIntTag());
			else reader.skipTag();
		});
		NBT_FIELDS.setField(TRANSFER_AMOUNT, (holder, reader) -> {
			if (holder instanceof Hopper)
			((Hopper) holder).setTransferAmount(reader.readShortTag());
		});
	}
	
	private int cooldown, transferAmount;
	
	public CoreHopper(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
	}

	@Override
	protected Inventory createInventory() {
		return ContainerFactory.GENERIC_INV_FACTORY.create(InventoryType.HOPPER, this);
	}

	@Override
	public int getTransferCooldown() {
		return cooldown;
	}

	@Override
	public void setTransferCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	@Override
	public int getTransferAmount() {
		return transferAmount;
	}

	@Override
	public void setTransferAmount(int amount) {
		this.transferAmount = amount;
	}

}
