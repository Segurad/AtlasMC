package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.Ladder;

public class CoreLadder extends CoreAbstractDirectional4Faces implements Ladder {

	private boolean waterlogged;
	
	public CoreLadder(Material material) {
		super(material);
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+getFaceValue()*2+(waterlogged?0:1);
	}

	@Override
	public boolean isWaterlogged() {
		return waterlogged;
	}

	@Override
	public void setWaterlogged(boolean waterlogged) {
		this.waterlogged = waterlogged;
	}

}
