package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Observer;

public class CoreObserver extends CoreDirectional6Faces implements Observer {

	private boolean powered;
	
	public CoreObserver(Material material) {
		super(material);
	}

	@Override
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(powered?0:1)+
				getFaceValue()*2;
	}

}
