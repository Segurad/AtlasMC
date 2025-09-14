package de.atlasmc.node.block.tile;

import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Hopper extends AbstractContainerTile<Inventory> {
	
	public static final NBTSerializationHandler<Hopper>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Hopper.class)
					.include(AbstractContainerTile.NBT_HANDLER)
					.intField("TransferCooldown", Hopper::getTransferCooldown, Hopper::setTransferCooldown)
					.build();
	
	int getTransferCooldown();
	
	void setTransferCooldown(int cooldown);
	
	@Override
	default NBTSerializationHandler<? extends Hopper> getNBTHandler() {
		return NBT_HANDLER;
	}

}
