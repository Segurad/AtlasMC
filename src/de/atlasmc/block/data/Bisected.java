package de.atlasmc.block.data;

public interface Bisected extends BlockData {
	
	public Half getHalf();
	public void setHalf(Half half);
	
	public static enum Half {
		BOTTOM,
		TOP
	}

}
