package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;
import de.atlasmc.block.data.Powerable;

public interface Bell extends Directional, Powerable {
	
	public Attachment getAttachment();
	public void setAttachment(Attachment attachment);
	
	public static enum Attachment {
		FLOOR,
		CEILING,
		SINGLE_WALL,
		DOUBLE_WALL;

		public static Attachment getByName(String name) {
			name = name.toUpperCase();
			for (Attachment a : values()) {
				if (a.name().equals(name)) return a;
			}
			return FLOOR;
		}
	}

}
