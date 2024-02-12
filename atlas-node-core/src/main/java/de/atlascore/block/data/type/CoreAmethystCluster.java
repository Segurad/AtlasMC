package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.type.AmethystCluster;

public class CoreAmethystCluster extends CoreDirectional6Faces implements AmethystCluster {

	private boolean waterlogged;
	
	public CoreAmethystCluster(Material material) {
		super(material, BlockFace.UP);
	}

	@Override
	public boolean isWaterlogged() {
		return waterlogged;
	}

	@Override
	public void setWaterlogged(boolean waterlogged) {
		this.waterlogged = waterlogged;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID() + (waterlogged?0:1) + getFaceValue()*2;
	}

}
