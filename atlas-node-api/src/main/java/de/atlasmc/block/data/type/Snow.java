package de.atlasmc.block.data.type;

import de.atlasmc.block.data.BlockData;

public interface Snow extends BlockData {
	
	public int getLayers();
	public int getMaxLayers();
	public int getMinLayers();
	public void setLayers(int layers);

}
