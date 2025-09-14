package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;

public interface Hopper extends Directional {
	
	public boolean isEnabled();
	public void setEnabled(boolean enabled);

}
