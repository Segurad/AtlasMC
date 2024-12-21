package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.NoteBlock;

class NoteProperty extends AbstractIntProperty {

	public NoteProperty() {
		super("note");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof NoteBlock noteBlock)
			noteBlock.setNote(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof NoteBlock noteBlock)
			return noteBlock.getNote();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof NoteBlock;
	}

}
