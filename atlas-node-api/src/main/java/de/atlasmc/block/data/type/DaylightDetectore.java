package de.atlasmc.block.data.type;

import de.atlasmc.block.data.AnaloguePowerable;

public interface DaylightDetectore extends AnaloguePowerable {
	
	public boolean isInverted();
	public void setInverted(boolean inverted);

}
