package de.atlasmc.block.data.type;

import de.atlasmc.block.data.BlockData;

public interface Jukebox extends BlockData {
	
	public boolean hasRecord();
	public void setRecord(boolean has);

}
