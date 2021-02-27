package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.RedstoneWallTorch;

public class CoreRedstoneWallTorch extends CoreDirectional4Faces implements RedstoneWallTorch {
	
	private boolean lit;
	
	public CoreRedstoneWallTorch(Material material) {
		super(material);
		lit = true;
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
	public int getStateID() {
		return getMaterial().getBlockID()+
				(lit?0:1)+
				getFaceValue()*2;
	}

}
