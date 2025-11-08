package de.atlasmc.core.node.block.data.type;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.atlasmc.core.node.block.data.CoreDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.ChiseledBookshelf;

public class CoreChiseledBookshelf extends CoreDirectional4Faces implements ChiseledBookshelf {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, 
				PropertyType.SLOT_0_OCCUPIED,
				PropertyType.SLOT_1_OCCUPIED,
				PropertyType.SLOT_2_OCCUPIED,
				PropertyType.SLOT_3_OCCUPIED,
				PropertyType.SLOT_4_OCCUPIED,
				PropertyType.SLOT_5_OCCUPIED);
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
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
