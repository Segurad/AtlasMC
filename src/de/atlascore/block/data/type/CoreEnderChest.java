package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.EnderChest;

public class CoreEnderChest extends CoreDirectional4Faces implements EnderChest {

	private boolean waterlogged;
	
	public CoreEnderChest(Material material) {
		super(material);
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
