package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;

public interface Hopper extends Directional {
	
	public boolean isEnabled();
	public void setEnabled(boolean enabled);

}
