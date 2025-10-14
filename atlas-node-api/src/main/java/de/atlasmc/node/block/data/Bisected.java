package de.atlasmc.node.block.data;

import de.atlasmc.IDHolder;
import de.atlasmc.util.EnumName;

public interface Bisected extends BlockData {
	
	Half getHalf();
	
	void setHalf(Half half);
	
	Bisected clone();
	
	public static enum Half implements EnumName, IDHolder {
		
		TOP(0),
		UPPER(0),
		BOTTOM(1),
		LOWER(1);
		
		private final int id;
		private final String name;
		
		private Half(int id) {
			this.id = id;
			name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public int getID() {
			return id;
		}

	}

}
