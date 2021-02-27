package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;
import de.atlasmc.block.data.Powerable;

public interface Repeater extends Directional, Powerable {

	public int getDelay();
	public int getMaxDelay();
	public int getMinDelay();
	public boolean isLocked();
	public void setDelay(int delay);
	public void setLocked(boolean locked);
	
}
