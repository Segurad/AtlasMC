package de.atlasmc.node.block.data.type;

import de.atlasmc.IDHolder;
import de.atlasmc.util.enums.EnumName;

public interface BigDripleaf extends Dripleaf {
	
	Tilt getTilt();
	
	void setTilt(Tilt tilt);
	
	BigDripleaf clone();
	
	public static enum Tilt implements IDHolder, EnumName {
		
		NONE,
		UNSTABLE,
		PARTIAL,
		FULL;
		
		private String name;
		
		private Tilt() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}

}
