package de.atlascore.block.tile;

import de.atlasmc.NamespacedKey;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.Crafter;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;

public class CoreCrafter extends CoreAbstractContainerTile<Inventory> implements Crafter {
	
	protected IntSet disabledSlots;
	protected int craftingTicks;
	protected boolean triggered;
	protected NamespacedKey lootTable;
	protected long lootTableSeed;
	
	public CoreCrafter(BlockType type) {
		super(type);
	}

	@Override
	public int getCraftingTicksRemaining() {
		return craftingTicks;
	}

	@Override
	public void setCraftingticksRemaining(int ticks) {
		craftingTicks = ticks;
	}

	@Override
	public IntSet getDisabledSlots() {
		if (disabledSlots == null)
			disabledSlots = new IntArraySet(9);
		return disabledSlots;
	}
	
	@Override
	public boolean hasDisabledSlots() {
		return disabledSlots != null && !disabledSlots.isEmpty();
	}

	@Override
	public boolean isSlotDisabled(int slot) {
		if (slot < 0 || slot > 8)
			throw new IllegalArgumentException("Slots must be between 0 and 8: " + slot);
		if (disabledSlots == null)
			return false;
		return disabledSlots.contains(slot);
	}

	@Override
	public void setSlotDisabled(int slot, boolean disabled) {
		if (slot < 0 || slot > 8)
			throw new IllegalArgumentException("Slots must be between 0 and 8: " + slot);
		if (disabled) {
			getDisabledSlots().add(slot);
		} else if (disabledSlots != null) {
			disabledSlots.remove(slot);
		}
	}

	@Override
	protected Inventory createInventory() {
		return ContainerFactory.CRAFTING_INV_FACTORY.create(this);
	}

	@Override
	public boolean isTriggered() {
		return triggered;
	}

	@Override
	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
	}

	@Override
	public NamespacedKey getLootTable() {
		return lootTable;
	}

	@Override
	public void setLootTable(NamespacedKey key) {
		this.lootTable = key;
	}

	@Override
	public long getLootTableSeed() {
		return lootTableSeed;
	}

	@Override
	public void setLootTableSeed(long seed) {
		this.lootTableSeed = seed;
	}

}
