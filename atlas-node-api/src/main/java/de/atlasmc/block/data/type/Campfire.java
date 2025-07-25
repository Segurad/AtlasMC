package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;
import de.atlasmc.block.data.Lightable;
import de.atlasmc.block.data.Waterlogged;

public interface Campfire extends Directional, Lightable, Waterlogged {
	
	boolean isSignalFire();
	
	void setSignalFire(boolean signalFire);

}
