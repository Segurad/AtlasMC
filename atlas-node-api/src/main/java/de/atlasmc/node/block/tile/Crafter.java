package de.atlasmc.node.block.tile;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.loot.LootTableHolder;
import it.unimi.dsi.fastutil.ints.IntSet;

public interface Crafter extends AbstractContainerTile<Inventory>, LootTableHolder {
	
	public static final NBTCodec<Crafter>
	NBT_HANDLER = NBTCodec
					.builder(Crafter.class)
					.include(AbstractContainerTile.NBT_HANDLER)
					.include(LootTableHolder.NBT_CODEC)
					.intField("crafting_ticks_remaining", Crafter::getCraftingTicksRemaining, Crafter::setCraftingticksRemaining, 0)
					.boolField("triggered", Crafter::isTriggered, Crafter::setTriggered, false)
					.codecCollection("disabled_slots", Crafter::hasDisabledSlots, Crafter::getDisabledSlots, NBTCodecs.INT_COLLECTION)
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
	default NBTCodec<? extends Crafter> getNBTCodec() {
		return NBT_HANDLER;
	}

}
