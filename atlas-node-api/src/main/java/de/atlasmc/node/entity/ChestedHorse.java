package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.util.annotation.NotNull;

public interface ChestedHorse extends AbstractHorse {
	
	@NotNull
	public static final NBTCodec<ChestedHorse>
	NBT_CODEC = NBTCodec
					.builder(ChestedHorse.class)
					.include(AbstractHorse.NBT_CODEC)
					.include(InventoryHolder.NBT_CODEC)
					.boolField("ChestedHorse", ChestedHorse::hasChest, ChestedHorse::setChest)
					.build();
	
	boolean hasChest();
	
	void setChest(boolean chest);
	
	@Override
	default NBTCodec<? extends ChestedHorse> getNBTCodec() {
		return NBT_CODEC;
	}

}
