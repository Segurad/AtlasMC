package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.NoteBlock;

class NoteProperty extends AbstractIntProperty {

	public NoteProperty() {
		super("note");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof NoteBlock noteBlock)
			noteBlock.setNote(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof NoteBlock noteBlock)
			return noteBlock.getNote();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof NoteBlock;
	}

}
