package de.atlascore.block.data.type;

import de.atlasmc.Material;
import de.atlasmc.block.data.type.Campfire;

import de.atlascore.block.data.CoreDirectional4Faces;

public class CoreCampfire extends CoreDirectional4Faces implements Campfire {

	private boolean lit, waterlogged, signalFire;
	
	public CoreCampfire(Material material) {
		super(material);
	}

	@Override
	public boolean isLit() {
		return lit;
	}

	@Override
	public void setLit(boolean lit) {
		this.lit = lit;
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
	public boolean isSignalFire() {
		return signalFire;
	}

	@Override
	public void setSignalFire(boolean signalFire) {
		this.signalFire = signalFire;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(waterlogged?0:1)+
				(signalFire?0:2)+
				(lit?0:4)+
				getFaceValue()*8;
	}

}
