package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;
import de.atlasmc.block.data.Powerable;

public interface Comparator extends Directional, Powerable {
	
	public Mode getMode();
	public void setMode(Mode mode);
	
	public static enum Mode {
		COMPARE,
		SUBTRACT;

		public static Mode getByName(String name) {
			return SUBTRACT.name().equalsIgnoreCase(name) ? SUBTRACT : COMPARE;
		}
	}

}
