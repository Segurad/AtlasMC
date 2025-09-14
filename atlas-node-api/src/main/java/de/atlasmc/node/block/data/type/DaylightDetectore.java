package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.AnaloguePowerable;

public interface DaylightDetectore extends AnaloguePowerable {
	
	public boolean isInverted();
	public void setInverted(boolean inverted);

}
