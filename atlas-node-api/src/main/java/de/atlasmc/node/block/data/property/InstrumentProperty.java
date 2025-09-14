package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Instrument;
import de.atlasmc.node.block.data.type.NoteBlock;

class InstrumentProperty extends AbstractEnumProperty<Instrument> {

	public InstrumentProperty() {
		super("instrument", Instrument.class);
	}

	@Override
	public void set(BlockData data, Instrument value) {
		if (data instanceof NoteBlock noteBlock)
			noteBlock.setInstrument(value);
	}

	@Override
	public Instrument get(BlockData data) {
		if (data instanceof NoteBlock noteBlock)
			return noteBlock.getInstrument();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof NoteBlock;
	}

}
