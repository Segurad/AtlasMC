package de.atlasmc.node.block.data;

public interface Attachable extends BlockData {
	
	boolean isAttached();
	
	void setAttached(boolean attached);

}
