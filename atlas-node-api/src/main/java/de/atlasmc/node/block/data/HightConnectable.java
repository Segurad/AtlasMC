package de.atlasmc.node.block.data;

import java.util.Set;

import de.atlasmc.node.block.BlockFace;
import de.atlasmc.util.enums.EnumName;

public interface HightConnectable extends BlockData {
	
	Height getHeight(BlockFace face);
	
	void setHeight(BlockFace face, Height height);
	
	Set<BlockFace> getAllowedFaces();
	
	public static enum Height implements EnumName {
		NONE,
		LOW,
		TALL;

		private String name;
		
		private Height() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}

	}

}
