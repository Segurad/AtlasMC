package de.atlasmc.node.block.data;

public interface Levelled extends BlockData {
	
	int getMaxLevel();
	
	int getLevel();
	
	void setLevel(int level);

}
