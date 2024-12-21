package de.atlasmc.block.data;

public interface Attachable extends BlockData {
	
	boolean isAttached();
	
	void setAttached(boolean attached);

}
