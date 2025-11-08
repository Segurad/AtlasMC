package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;
import de.atlasmc.util.enums.EnumName;

public interface Bed extends Directional {
	
	boolean isOccupied();
	
	void setOccupied(boolean occupied);
	
	Part getPart();
	
	void setPart(Part part);
	
	public static enum Part implements EnumName {
		
		HEAD,
		FOOT;

		private String name;
		
		private Part() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}

	}

}
