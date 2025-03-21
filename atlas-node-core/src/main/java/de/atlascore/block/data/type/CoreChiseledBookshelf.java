package de.atlascore.block.data.type;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.ChiseledBookshelf;

public class CoreChiseledBookshelf extends CoreDirectional4Faces implements ChiseledBookshelf {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, 
				BlockDataProperty.SLOT_0_OCCUPIED,
				BlockDataProperty.SLOT_1_OCCUPIED,
				BlockDataProperty.SLOT_2_OCCUPIED,
				BlockDataProperty.SLOT_3_OCCUPIED,
				BlockDataProperty.SLOT_4_OCCUPIED,
				BlockDataProperty.SLOT_5_OCCUPIED);
	}
	
	private Set<Integer> slots;
	
	public CoreChiseledBookshelf(BlockType type) {
		super(type);
		slots = new HashSet<>(getMaxOccupiedSlots());
	}
	
	@Override
	public CoreChiseledBookshelf clone() {
		CoreChiseledBookshelf clone = (CoreChiseledBookshelf) super.clone();
		clone.slots = new HashSet<>(clone.getMaxOccupiedSlots());
		clone.slots.addAll(slots);
		return clone;
	}

	@Override
	public int getMaxOccupiedSlots() {
		return 6;
	}

	@Override
	public Set<Integer> getOccupiedSots() {
		return slots;
	}

	@Override
	public boolean isSlotOccupied(int slot) {
		if (slot >= getMaxOccupiedSlots() || slot < 0)
			throw new IllegalArgumentException("Slot must be between 0 and " + getMaxOccupiedSlots() + ": " + slot);
		return slots.contains(slot);
	}

	@Override
	public void setSlotOccupied(int slot, boolean occupied) {
		if (slot >= getMaxOccupiedSlots() || slot < 0)
			throw new IllegalArgumentException("Slot must be between 0 and " + getMaxOccupiedSlots() + ": " + slot);
		if (occupied) {
			slots.add(slot);
		} else {
			slots.remove(slot);
		}
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID() + 
				(isSlotOccupied(0)?0:32) +
				(isSlotOccupied(1)?0:16) +
				(isSlotOccupied(2)?0:8) +
				(isSlotOccupied(3)?0:4) +
				(isSlotOccupied(4)?0:2) +
				(isSlotOccupied(5)?0:1) +
				getFaceValue()*64;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
