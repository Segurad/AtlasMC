package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CorePowerable;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Instrument;
import de.atlasmc.node.block.data.type.NoteBlock;

public class CoreNoteBlock extends CorePowerable implements NoteBlock {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CorePowerable.PROPERTIES, 
				PropertyType.INSTRUMENT,
				PropertyType.NOTE);
	}
	
	private Instrument instrument;
	private byte note;
	
	public CoreNoteBlock(BlockType type) {
		super(type);
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
		return type.getBlockStateID()+instrument.ordinal()*48+note*2+(powered?0:1);
	}

	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}
	
}
