package de.atlasmc.block.data.type;

import de.atlasmc.block.data.BlockData;

public interface StructureBlock extends BlockData {
	
	public Mode getMode();
	public void setMode(Mode mode);
	
	public static enum Mode {
		SAVE,
		LOAD,
		CORNER,
		DATA
	}

}
