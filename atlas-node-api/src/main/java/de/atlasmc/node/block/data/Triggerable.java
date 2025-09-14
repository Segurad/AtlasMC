package de.atlasmc.node.block.data;

public interface Triggerable extends BlockData {
	
	boolean isTriggered();
	
	void setTriggered(boolean triggered);

}
