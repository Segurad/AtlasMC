package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Ageable;
import de.atlasmc.util.EnumName;

public interface Bamboo extends Ageable, Sapling {

	Leaves getLeaves();
	
	void setLeaves(Leaves leaves);
	
	public static enum Leaves implements EnumName {
		
		NONE,
		SMALL,
		LARGE;
		
		private String name;
		
		private Leaves() {
			this.name = name().toLowerCase().intern();
		}

		@Override
		public String getName() {
			return name;
		}
		
	}
}
