package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.EndPortalFrame;

public class CoreEndPortalFrame extends CoreDirectional4Faces implements EndPortalFrame {

	private boolean eye;
	
	public CoreEndPortalFrame(Material material) {
		super(material);
	}

	@Override
	public boolean hasEye() {
		return eye;
	}

	@Override
	public void setEye(boolean eye) {
		this.eye = eye;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(eye?0:1)+
				getFaceValue()*2;
	}

}
