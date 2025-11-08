package de.atlasmc.node.block.data.type;

import java.util.Set;

import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.block.data.AnaloguePowerable;
import de.atlasmc.util.enums.EnumName;

public interface RedstoneWire extends AnaloguePowerable {
	
	Set<BlockFace> getAllowedFaces();
	
	Connection getFace(BlockFace face);
	
	void setFace(BlockFace face, Connection connection);
	
	public static enum Connection implements EnumName {
		
		UP,
		SIDE,
		NONE;

		private String name;
		
		private Connection() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}

	}

}
