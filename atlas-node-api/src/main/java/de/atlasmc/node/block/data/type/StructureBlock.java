package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.util.enums.EnumName;

public interface StructureBlock extends BlockData {
	
	Mode getMode();
	
	void setMode(Mode mode);
	
	public static enum Mode implements EnumName {
		
		SAVE,
		LOAD,
		CORNER,
		DATA;

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
