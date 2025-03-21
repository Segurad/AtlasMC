package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Powerable;

public interface NoteBlock extends Powerable {
	
	Instrument getInstrument();
	
	int getNote();
	
	void setInstrument(Instrument instrument);
	
	void setNote(int note);

}
