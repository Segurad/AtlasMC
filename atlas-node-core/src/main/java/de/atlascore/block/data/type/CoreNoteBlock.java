package de.atlascore.block.data.type;

import java.io.IOException;

import de.atlascore.block.data.CorePowerable;
import de.atlasmc.Instrument;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.NoteBlock;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreNoteBlock extends CorePowerable implements NoteBlock {

	protected static final CharKey
	INSTRUMENT = CharKey.literal("instrument"),
	NOTE = CharKey.literal("note");
	
	static {
		NBT_FIELDS.setField(INSTRUMENT, (holder, reader) -> {
			if (holder instanceof NoteBlock)
			((NoteBlock) holder).setInstrument(Instrument.getByName(reader.readStringTag()));
			else reader.skipTag();
		});
		NBT_FIELDS.setField(NOTE, (holder, reader) -> {
			if (holder instanceof NoteBlock)
			((NoteBlock) holder).setNote(reader.readIntTag());
			else reader.skipTag();
		});
	}
	
	private Instrument instrument;
	private byte note;
	
	public CoreNoteBlock(Material material) {
		super(material);
		this.instrument = Instrument.HARP;
	}

	@Override
	public Instrument getInstrument() {
		return instrument;
	}

	@Override
	public int getNote() {
		return note;
	}

	@Override
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	@Override
	public void setNote(int note) {
		if (note < 0 || note > 24) throw new IllegalArgumentException("Note is not between 0 and 24: " + note);
		this.note = (byte) note;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+instrument.ordinal()*48+note*2+(isPowered()?0:1);
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getInstrument() != Instrument.HARP) writer.writeStringTag(INSTRUMENT, getInstrument().name().toLowerCase());
		if (getNote() > 0) writer.writeIntTag(NOTE, getNote());
	}
	
}
