package de.atlasmc.node.block.data;

public interface Brushable extends BlockData {
	
	int getDusted();
	
	int getMaxDusted();
	
	void setDusted(int dusted);
	
	Brushable clone();

}
