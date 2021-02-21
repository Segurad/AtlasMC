package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Furnace;

public class CoreFurnace extends CoreDirectional4Faces implements Furnace {

	private boolean lit;
	
	public CoreFurnace(Material material) {
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
	public int getStateID() {
		return getMaterial().getBlockID()+
				(lit?0:1)+
				getFaceValue()*2;
	}

}
