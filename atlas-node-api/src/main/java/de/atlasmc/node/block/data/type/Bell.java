package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;
import de.atlasmc.node.block.data.Powerable;
import de.atlasmc.util.enums.EnumName;

public interface Bell extends Directional, Powerable {
	
	Attachment getAttachment();
	
	void setAttachment(Attachment attachment);
	
	public static enum Attachment implements EnumName {
		
		FLOOR,
		CEILING,
		SINGLE_WALL,
		DOUBLE_WALL;

		private String name;
		
		private Attachment() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}

}
