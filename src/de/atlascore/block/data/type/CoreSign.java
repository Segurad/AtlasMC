package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreRotatable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Sign;

public class CoreSign extends CoreRotatable implements Sign {

	private boolean waterlogged;
	
	public CoreSign(Material material) {
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
				getRotationValue()*2;
	}

}
