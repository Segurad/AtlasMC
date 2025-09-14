package de.atlasmc.node.block.data;

public interface Lightable extends BlockData {
	
	boolean isLit();
	
	void setLit(boolean lit);

}
