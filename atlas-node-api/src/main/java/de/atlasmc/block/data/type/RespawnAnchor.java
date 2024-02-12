package de.atlasmc.block.data.type;

import de.atlasmc.block.data.BlockData;

public interface RespawnAnchor extends BlockData {
	
	public int getCharges();
	public int getMaxCharges();
	public void setCharges(int charges);

}
