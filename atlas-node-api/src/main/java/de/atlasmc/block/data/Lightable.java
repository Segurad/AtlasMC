package de.atlasmc.block.data;

public interface Lightable extends BlockData {
	
	boolean isLit();
	
	void setLit(boolean lit);

}
