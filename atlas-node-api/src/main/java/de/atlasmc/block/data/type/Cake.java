package de.atlasmc.block.data.type;

import de.atlasmc.block.data.BlockData;

public interface Cake extends BlockData {
	
	public int getBites();
	public int getMaxBites();
	public void setBites(int bites);

}
