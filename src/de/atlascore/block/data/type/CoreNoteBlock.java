package de.atlascore.block.data.type;

import de.atlascore.block.data.CorePowerable;
import de.atlasmc.Instrument;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.NoteBlock;
import de.atlasmc.util.Validate;

public class CoreNoteBlock extends CorePowerable implements NoteBlock {

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
		Validate.isTrue(note > 0 && note < 25, "Note is not between 0 and 24: " + note);
		this.note = (byte) note;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+instrument.ordinal()*48+note*2+(isPowered()?0:1);
	}

}
