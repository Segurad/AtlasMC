package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;

public class CoreBlockData implements BlockData {
	
	private final Material mat;
	
	public CoreBlockData(Material material) {
		this.mat = material;
	}
	
	public BlockData clone() {
		try {
			return (BlockData) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Material getMaterial() {
		return mat;
	}

	@Override
	public boolean matches(BlockData data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getStateID() {
		return mat.getBlockID();
	}

}
