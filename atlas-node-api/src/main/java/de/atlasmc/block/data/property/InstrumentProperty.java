package de.atlasmc.block.data.property;

import java.io.IOException;

import de.atlasmc.Instrument;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.NoteBlock;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

class InstrumentProperty extends AbstractEnumProperty<Instrument> {

	public InstrumentProperty() {
		super("instrument");
	}

	@Override
	public Instrument fromNBT(NBTReader reader) throws IOException {
		return Instrument.getByName(reader.readStringTag());
	}

	@Override
	public void toNBT(Instrument value, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeStringTag(key, value.getName());
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
