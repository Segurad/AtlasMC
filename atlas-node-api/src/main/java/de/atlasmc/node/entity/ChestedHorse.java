package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.InventoryHolder;

public interface ChestedHorse extends AbstractHorse {
	
	public static final NBTCodec<ChestedHorse>
	NBT_HANDLER = NBTCodec
					.builder(ChestedHorse.class)
					.include(AbstractHorse.NBT_HANDLER)
					.include(InventoryHolder.NBT_HANDLER)
					.boolField("ChestedHorse", ChestedHorse::hasChest, ChestedHorse::setChest)
					.build();
	
	boolean hasChest();
	
	void setChest(boolean chest);
	
	@Override
	default NBTCodec<? extends ChestedHorse> getNBTCodec() {
		return NBT_HANDLER;
	}

}
