package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CorePowerable;
import de.atlasmc.Instrument;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.NoteBlock;

public class CoreNoteBlock extends CorePowerable implements NoteBlock {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CorePowerable.PROPERTIES, 
				BlockDataProperty.INSTRUMENT,
				BlockDataProperty.NOTE);
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
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
}
