package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;
import de.atlasmc.util.EnumName;

public interface TechnicalPiston extends Directional {
	
	Type getPistonType();
	
	void setPistonType(Type type);
	
	public static enum Type implements EnumName {
		
		NORMAL,
		STICKY;

		private String name;
		
		private Type() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}

}
