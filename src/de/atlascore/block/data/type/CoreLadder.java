package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Ladder;

public class CoreLadder extends CoreDirectional4Faces implements Ladder {

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
