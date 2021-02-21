package de.atlasmc.block.data.type;

import de.atlasmc.block.data.BlockData;

public interface Leaves extends BlockData {
	
	public int getDistance();
	public boolean isPersistent();
	public void setDistance(int distance);
	public void setPersistent(boolean persistent);

}
