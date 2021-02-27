package de.atlasmc.block.data.type;

import de.atlasmc.block.data.BlockData;

public interface TurtleEgg extends BlockData {
	
	public int getEggs();
	public int getHatch();
	public int getMaxEggs();
	public int getMaxHatch();
	public int getMinEggs();
	public void setHatch(int hatch);
	public void setEggs(int eggs);

}
