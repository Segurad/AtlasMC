package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.DecoratedPot;

public class CoreDecoratedPot extends CoreDirectional4Faces implements DecoratedPot {

	private boolean waterlogged;
	private boolean cracked;
	
	public CoreDecoratedPot(Material material) {
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
	public boolean isCracked() {
		return cracked;
	}

	@Override
	public void setCracked(boolean cracked) {
		this.cracked = cracked;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID() + (waterlogged?0:1) + (getFaceValue()*2) + (cracked?0:8);
	}
	
	@Override
	public CoreDecoratedPot clone() {
		return (CoreDecoratedPot) super.clone();
	}

}
