package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.Dispenser;

public class CoreDispenser extends CoreAbstractDirectional6Faces implements Dispenser {

	public boolean triggered;
	
	public CoreDispenser(Material material) {
		super(material);
	}

	@Override
	public boolean isTriggered() {
		return triggered;
	}

	@Override
	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+getFaceValue(getFacing())*2+(triggered?0:1);
	}

}
