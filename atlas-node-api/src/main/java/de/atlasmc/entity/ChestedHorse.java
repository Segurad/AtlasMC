package de.atlasmc.entity;

import de.atlasmc.inventory.InventoryHolder;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ChestedHorse extends AbstractHorse {
	
	public static final NBTSerializationHandler<ChestedHorse>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ChestedHorse.class)
					.include(AbstractHorse.NBT_HANDLER)
					.include(InventoryHolder.NBT_HANDLER)
					.boolField("ChestedHorse", ChestedHorse::hasChest, ChestedHorse::setChest)
					.build();
	
	boolean hasChest();
	
	void setChest(boolean chest);
	
	@Override
	default NBTSerializationHandler<? extends ChestedHorse> getNBTHandler() {
		return NBT_HANDLER;
	}

}
