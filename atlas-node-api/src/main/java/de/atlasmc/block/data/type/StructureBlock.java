package de.atlasmc.block.data.type;

import de.atlasmc.block.data.BlockData;

public interface StructureBlock extends BlockData {
	
	public Mode getMode();
	public void setMode(Mode mode);
	
	public static enum Mode {
		SAVE,
		LOAD,
		CORNER,
		DATA;

		public static Mode getByName(String name) {
			name = name.toUpperCase();
			for (Mode m : values()) {
				if (m.name().equals(name)) return m;
			}
			return SAVE;
		}
	}

}
