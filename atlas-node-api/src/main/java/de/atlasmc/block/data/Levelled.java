package de.atlasmc.block.data;

public interface Levelled extends BlockData {
	
	public int getMaxLevel();
	public int getLevel();
	public void setLevel(int level);

}
