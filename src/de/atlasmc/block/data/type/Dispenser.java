package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;

public interface Dispenser extends Directional {
	
	public boolean isTriggered();
	public void setTriggered(boolean triggered);

}
