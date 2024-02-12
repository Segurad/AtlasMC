package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.type.LightningRod;

public class CoreLightningRod extends CoreDirectional6Faces implements LightningRod {

	private boolean waterlogged;
	private boolean powered;
	
	public CoreLightningRod(Material material) {
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
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID() + (waterlogged?0:1) + (powered?0:2) + getFaceValue()*4;
	}

}
