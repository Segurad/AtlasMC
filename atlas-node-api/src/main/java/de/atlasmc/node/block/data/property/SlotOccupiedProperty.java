package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.ChiseledBookshelf;

class SlotOccupiedProperty extends AbstractBooleanProperty {

	private int slot;
	
	public SlotOccupiedProperty(String key, int slot) {
		super(key);
		this.slot = slot;
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof ChiseledBookshelf shelf)
			shelf.setSlotOccupied(slot, value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof ChiseledBookshelf shelf)
			return shelf.isSlotOccupied(slot);
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof ChiseledBookshelf;
	}

}
