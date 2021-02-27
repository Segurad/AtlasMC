package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Barrel;

public class CoreBarrel extends CoreDirectional6Faces implements Barrel {

	private boolean open;
	
	public CoreBarrel(Material material) {
		super(material);
	}

	@Override
	public boolean isOpen() {
		return open;
	}

	@Override
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(open?0:1)+
				getFaceValue()*2;
	}

}
