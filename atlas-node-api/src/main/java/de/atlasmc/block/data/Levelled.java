package de.atlasmc.block.data;

public interface Levelled extends BlockData {
	
	int getMaxLevel();
	
	int getLevel();
	
	void setLevel(int level);

}
