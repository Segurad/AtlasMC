package de.atlascore.block.tile;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.Hopper;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;

public class CoreHopper extends CoreAbstractContainerTile<Inventory> implements Hopper {

	protected static final NBTFieldSet<CoreHopper> NBT_FIELDS;
	
	protected static final CharKey
	TRANSFER_COOLDOWN = CharKey.literal("TransferCooldown"),
	TRANSFER_AMOUNT = CharKey.literal("TransferAmount");
	
	static {
		NBT_FIELDS = CoreAbstractContainerTile.NBT_FIELDS.fork();
		NBT_FIELDS.setField(TRANSFER_COOLDOWN, (holder, reader) -> {
			holder.setTransferCooldown(reader.readIntTag());
		});
		NBT_FIELDS.setField(TRANSFER_AMOUNT, (holder, reader) -> {
			holder.setTransferAmount(reader.readShortTag());
		});
	}
	
	private int cooldown;
	private int transferAmount;
	
	public CoreHopper(BlockType type) {
		super(type);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreHopper> getFieldSetRoot() {
		return NBT_FIELDS;
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
