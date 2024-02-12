package de.atlasmc.block.data;

public interface Hatchable extends BlockData {
	
	int getHatch();
	
	int getMaxHatch();
	
	void setHatch(int hatch);

}
