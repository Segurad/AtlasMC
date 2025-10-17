package de.atlasmc.node.block.tile;

import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.util.nbt.codec.NBTCodec;

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
