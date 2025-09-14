package de.atlasmc.node.block.tile;

import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.loot.LootTableHolder;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;
import it.unimi.dsi.fastutil.ints.IntSet;

public interface Crafter extends AbstractContainerTile<Inventory>, LootTableHolder {
	
	public static final NBTSerializationHandler<Crafter>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Crafter.class)
					.include(AbstractContainerTile.NBT_HANDLER)
					.include(LootTableHolder.NBT_HANDLER)
					.intField("crafting_ticks_remaining", Crafter::getCraftingTicksRemaining, Crafter::setCraftingticksRemaining, 0)
					.boolField("triggered", Crafter::isTriggered, Crafter::setTriggered, false)
					.intSetField("disabled_slots", Crafter::hasDisabledSlots, Crafter::getDisabledSlots)
					.build();
	
	int getCraftingTicksRemaining();
	
	void setCraftingticksRemaining(int ticks);
	
	IntSet getDisabledSlots();
	
	boolean hasDisabledSlots();
	
	boolean isSlotDisabled(int slot);
	
	void setSlotDisabled(int slot, boolean disabled);
	
	boolean isTriggered();
	
	void setTriggered(boolean triggered);
	
	@Override
	default NBTSerializationHandler<? extends Crafter> getNBTHandler() {
		return NBT_HANDLER;
	}

}
