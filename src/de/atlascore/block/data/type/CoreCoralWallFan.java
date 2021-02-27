package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.CoralWallFan;

public class CoreCoralWallFan extends CoreDirectional4Faces implements CoralWallFan {

	private boolean waterlogged;
	
	public CoreCoralWallFan(Material material) {
		super(material);
		this.waterlogged = true;
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
		return getMaterial().getBlockID()+
				(waterlogged?0:1)+
				getFaceValue()*2;
	}

}
