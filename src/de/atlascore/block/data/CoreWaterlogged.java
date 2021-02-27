package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.Waterlogged;

public class CoreWaterlogged extends CoreBlockData implements Waterlogged {

	private boolean waterlogged;
	
	public CoreWaterlogged(Material material) {
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
		return super.getStateID()+(waterlogged?0:1);
	}

}
