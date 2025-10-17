package de.atlasmc.node.entity;

import de.atlasmc.node.inventory.InventoryHolder;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Allay extends Creature, InventoryHolder {
	
	public static final NBTCodec<Allay>
	NBT_HANDLER = NBTCodec
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
	default NBTCodec<? extends Allay> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
