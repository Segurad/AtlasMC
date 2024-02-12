package de.atlasmc.block.data.type;

import de.atlasmc.Instrument;
import de.atlasmc.block.data.Powerable;

public interface NoteBlock extends Powerable {
	
	public Instrument getInstrument();
	public int getNote();
	public void setInstrument(Instrument instrument);
	public void setNote(int note);

}
