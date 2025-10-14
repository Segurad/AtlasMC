package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;
import de.atlasmc.node.block.data.Powerable;
import de.atlasmc.util.EnumName;

public interface Comparator extends Directional, Powerable {
	
	Mode getMode();
	
	void setMode(Mode mode);
	
	public static enum Mode implements EnumName {
		COMPARE,
		SUBTRACT;

		private String name;
		
		private Mode() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}

}
