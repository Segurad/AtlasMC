package de.atlascore.block.data.type;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import de.atlascore.block.data.CoreBlockData;
import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.ChiseledBookshelf;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreChiseledBookshelf extends CoreDirectional4Faces implements ChiseledBookshelf {

	protected static final ChildNBTFieldContainer<CoreChiseledBookshelf> NBT_FIELDS;
	
	protected static final CharKey
	NBT_SLOT_0_OCCUPIED = CharKey.literal("slot_0_occupied"),
	NBT_SLOT_1_OCCUPIED = CharKey.literal("slot_1_occupied"),
	NBT_SLOT_2_OCCUPIED = CharKey.literal("slot_2_occupied"),
	NBT_SLOT_3_OCCUPIED = CharKey.literal("slot_3_occupied"),
	NBT_SLOT_4_OCCUPIED = CharKey.literal("slot_4_occupied"),
	NBT_SLOT_5_OCCUPIED = CharKey.literal("slot_5_occupied");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreDirectional4Faces.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_SLOT_0_OCCUPIED, (holder, reader) -> {
			holder.setSlotOccupied(0, reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_SLOT_1_OCCUPIED, (holder, reader) -> {
			holder.setSlotOccupied(1, reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_SLOT_2_OCCUPIED, (holder, reader) -> {
			holder.setSlotOccupied(2, reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_SLOT_3_OCCUPIED, (holder, reader) -> {
			holder.setSlotOccupied(3, reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_SLOT_4_OCCUPIED, (holder, reader) -> {
			holder.setSlotOccupied(4, reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_SLOT_5_OCCUPIED, (holder, reader) -> {
			holder.setSlotOccupied(5, reader.readByteTag() == 1);
		});
	}
	
	private Set<Integer> slots;
	
	public CoreChiseledBookshelf(Material material) {
		super(material);
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
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isSlotOccupied(0))
			writer.writeByteTag(NBT_SLOT_0_OCCUPIED, true);
		if (isSlotOccupied(1))
			writer.writeByteTag(NBT_SLOT_1_OCCUPIED, true);
		if (isSlotOccupied(2))
			writer.writeByteTag(NBT_SLOT_2_OCCUPIED, true);
		if (isSlotOccupied(3))
			writer.writeByteTag(NBT_SLOT_3_OCCUPIED, true);
		if (isSlotOccupied(4))
			writer.writeByteTag(NBT_SLOT_4_OCCUPIED, true);
		if (isSlotOccupied(5))
			writer.writeByteTag(NBT_SLOT_5_OCCUPIED, true);
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID() + 
				(isSlotOccupied(0)?0:32) +
				(isSlotOccupied(1)?0:16) +
				(isSlotOccupied(2)?0:8) +
				(isSlotOccupied(3)?0:4) +
				(isSlotOccupied(4)?0:2) +
				(isSlotOccupied(5)?0:1) +
				getFaceValue()*64;
	}
	
	@Override
	protected NBTFieldContainer<? extends CoreBlockData> getFieldContainerRoot() {
		return NBT_FIELDS;
	}

}
