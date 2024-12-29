package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Crafter;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;

public class CoreCrafter extends CoreAbstractContainerTile<Inventory> implements Crafter {

	protected static final NBTFieldContainer<CoreCrafter> NBT_FIELDS;
	
	protected static final CharKey
	NBT_CRAFTING_TICKS_REMAINING = CharKey.literal("crafting_ticks_remaining"),
	NBT_TRIGGERED = CharKey.literal("triggered"),
	NBT_DISABLED_SLOTS = CharKey.literal("disabled_slots");
	
	static {
		NBT_FIELDS = CoreAbstractContainerTile.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_CRAFTING_TICKS_REMAINING, (holder, reader) -> {
			holder.setCraftingticksRemaining(reader.readIntTag());
		});
		NBT_FIELDS.setField(NBT_TRIGGERED, (holder, reader) -> {
			holder.setTriggered(reader.readBoolean());
		});
		NBT_FIELDS.setField(NBT_DISABLED_SLOTS, (holder, reader) -> {
			int[] disabled = reader.readIntArrayTag();
			for (int i : disabled)
				holder.setSlotDisabled(i, true);
		});
	}
	
	protected IntSet disabledSlots;
	protected int craftingTicks;
	protected boolean triggered;
	
	public CoreCrafter(Material type) {
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
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!systemData)
			return;
		if (disabledSlots != null && !disabledSlots.isEmpty()) {
			writer.writeIntArrayTag(NBT_DISABLED_SLOTS, disabledSlots.toIntArray());
		}
		if (triggered)
			writer.writeByteTag(NBT_TRIGGERED, true);
		if (craftingTicks != 0)
			writer.writeIntTag(NBT_CRAFTING_TICKS_REMAINING, craftingTicks);
	}

}
