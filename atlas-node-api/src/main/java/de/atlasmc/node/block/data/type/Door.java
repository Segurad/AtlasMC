package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Bisected;
import de.atlasmc.node.block.data.Directional;
import de.atlasmc.node.block.data.Openable;
import de.atlasmc.node.block.data.Powerable;
import de.atlasmc.util.EnumName;

public interface Door extends Bisected, Directional, Openable, Powerable {
	
	Hinge getHinge();
	
	void setHinge(Hinge hinge);
	
	public static enum Hinge implements EnumName {
		
		LEFT,
		RIGHT;

		private String name;
		
		private Hinge() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
	
	}

}
