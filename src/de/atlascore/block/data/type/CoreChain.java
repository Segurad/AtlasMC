package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreOrientable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Chain;

public class CoreChain extends CoreOrientable implements Chain {

	private boolean waterlogged;
	
	public CoreChain(Material material) {
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
				getAxis().ordinal()*2;
	}

}
