package de.atlasmc.node.block.tile;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.util.annotation.NotNull;

public interface Hopper extends AbstractContainerTile<Inventory> {
	
	@NotNull
	public static final NBTCodec<Hopper>
	NBT_CODEC = NBTCodec
					.builder(Hopper.class)
					.include(AbstractContainerTile.NBT_CODEC)
					.intField("TransferCooldown", Hopper::getTransferCooldown, Hopper::setTransferCooldown)
					.build();
	
	int getTransferCooldown();
	
	void setTransferCooldown(int cooldown);
	
	@Override
	default NBTCodec<? extends Hopper> getNBTCodec() {
		return NBT_CODEC;
	}

}
