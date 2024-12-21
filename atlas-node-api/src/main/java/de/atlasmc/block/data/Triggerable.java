package de.atlasmc.block.data;

public interface Triggerable extends BlockData {
	
	boolean isTriggered();
	
	void setTriggered(boolean triggered);

}
