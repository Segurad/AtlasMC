package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;

public interface EndPortalFrame extends Directional {
	
	public boolean hasEye();
	public void setEye(boolean eye);

}
