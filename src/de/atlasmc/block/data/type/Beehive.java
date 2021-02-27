package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;

public interface Beehive extends Directional {
	
	public int getHoneyLevel();
	public int getMaxHoneyLevel();
	public void setHoneyLevel(int honeyLevel);

}
