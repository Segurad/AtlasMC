package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;
import de.atlasmc.node.block.data.Lightable;
import de.atlasmc.node.block.data.Waterlogged;

public interface Campfire extends Directional, Lightable, Waterlogged {
	
	boolean isSignalFire();
	
	void setSignalFire(boolean signalFire);

}
