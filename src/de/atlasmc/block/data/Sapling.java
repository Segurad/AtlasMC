package de.atlasmc.block.data;

public interface Sapling extends BlockData {

	public int getMaxStage();
	public int getStage();
	public void setStage(int stage);
}
