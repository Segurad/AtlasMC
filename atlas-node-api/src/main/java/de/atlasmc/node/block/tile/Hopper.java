package de.atlasmc.node.block.tile;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.Inventory;

public interface Hopper extends AbstractContainerTile<Inventory> {
	
	public static final NBTCodec<Hopper>
	NBT_HANDLER = NBTCodec
					.builder(Hopper.class)
					.include(AbstractContainerTile.NBT_HANDLER)
					.intField("TransferCooldown", Hopper::getTransferCooldown, Hopper::setTransferCooldown)
					.build();
	
	int getTransferCooldown();
	
	void setTransferCooldown(int cooldown);
	
	@Override
	default NBTCodec<? extends Hopper> getNBTCodec() {
		return NBT_HANDLER;
	}

}
