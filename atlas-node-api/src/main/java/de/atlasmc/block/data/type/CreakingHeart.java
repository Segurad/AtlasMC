package de.atlasmc.block.data.type;

import de.atlasmc.block.data.AxisOrientable;

public interface CreakingHeart extends AxisOrientable {
	
	boolean isActive();
	
	void setActive(boolean active);
	
	boolean isNatural();
	
	void setNatural(boolean natural);
	
	CreakingHeart clone();

}
