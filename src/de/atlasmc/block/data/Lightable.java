package de.atlasmc.block.data;

public interface Lightable extends BlockData {
	
	public boolean isLit();
	public void setLit(boolean lit);

}
