package de.atlasmc.block.data;

import de.atlasmc.Instrument;

public interface NoteBlock extends Powerable {
	
	public Instrument getInstrument();
	public int getNote();
	public void setInstrument(Instrument instrument);
	public void setNote(int note);

}
