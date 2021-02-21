package de.atlasmc.block.data.type;

import de.atlasmc.block.data.BlockData;

public interface Sapling extends BlockData {

	public int getMaxStage();
	public int getStage();
	public void setStage(int stage);
}
