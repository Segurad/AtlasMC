package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreLevelled;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Light;

public class CoreLight extends CoreLevelled implements Light {

	private boolean waterlogged;
	
	public CoreLight(Material material) {
		super(material, 15);
		setLevel(15);
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
		return getMaterial().getBlockStateID() + (waterlogged?0:1) + getLevel()*2;
	}
	
}
