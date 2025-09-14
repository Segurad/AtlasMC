package de.atlasmc.node.block.data;

public interface Distanced extends BlockData {
	
	int getDistance();
	
	void setDistance(int distance);
	
	int getMaxDistance();

}
