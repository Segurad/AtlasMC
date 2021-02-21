package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Piston;

public class CorePiston extends CoreDirectional6Faces implements Piston {

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
