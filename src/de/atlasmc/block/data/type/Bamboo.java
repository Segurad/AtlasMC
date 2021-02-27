package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Ageable;

public interface Bamboo extends Ageable, Sapling {

	public Leaves getLeaves();
	public void setLeaves(Leaves leaves);
	
	public static enum Leaves {
		NONE,
		SMALL,
		LARGE
	}
}
