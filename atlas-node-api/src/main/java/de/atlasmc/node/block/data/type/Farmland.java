package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.BlockData;

public interface Farmland extends BlockData {
	
	public int getMoisture();
	public int getMaxMoisture();
	public void setMoisture(int moisture);

}
