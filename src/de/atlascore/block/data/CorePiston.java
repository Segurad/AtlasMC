package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.Piston;

public class CorePiston extends CoreAbstractDirectional6Faces implements Piston {

	private boolean extended;
	
	public CorePiston(Material material) {
		super(material);
	}

	@Override
	public boolean isExtended() {
		return extended;
	}

	@Override
	public void setExtended(boolean extended) {
		this.extended = extended;
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+getFaceValue()+(extended?0:6);
	}

}
