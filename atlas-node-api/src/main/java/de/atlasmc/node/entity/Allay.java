package de.atlasmc.node.entity;

import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Allay extends Creature, InventoryHolder {
	
	public static final NBTSerializationHandler<Allay>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Allay.class)
					.include(Creature.NBT_HANDLER)
					.include(InventoryHolder.NBT_HANDLER)
					.longField("DuplicationCooldown", Allay::getDuplicationCooldown, Allay::setDuplicationCooldown, 0)
					.boolField("CanDuplicate", Allay::canDuplicate, Allay::setCanDuplicate, false) // non standard
					.build();
	
	boolean canDuplicate();
	
	void setCanDuplicate(boolean canDuplicate);
	
	long getDuplicationCooldown();
	
	void setDuplicationCooldown(long cooldown);
	
	@Override
	default NBTSerializationHandler<? extends Allay> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
