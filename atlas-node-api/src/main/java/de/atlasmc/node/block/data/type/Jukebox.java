package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.BlockData;

public interface Jukebox extends BlockData {
	
	public boolean hasRecord();
	public void setRecord(boolean has);

}
