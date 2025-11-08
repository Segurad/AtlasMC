package de.atlasmc.node.block.data;

import de.atlasmc.util.enums.EnumName;

public interface FaceAttachable extends BlockData {

	AttachedFace getAttachedFace();
	
	void setAttachedFace(AttachedFace face);
	
	public static enum AttachedFace implements EnumName {

		FLOOR,
		WALL,
		CEILING;

		private String name;
		
		private AttachedFace() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}

	}
}
