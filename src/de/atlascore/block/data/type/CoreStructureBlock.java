package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.StructureBlock;
import de.atlasmc.util.Validate;

public class CoreStructureBlock extends CoreBlockData implements StructureBlock {

	private Mode mode;
	
	public CoreStructureBlock(Material material) {
		super(material);
		mode = Mode.SAVE;
	}

	@Override
	public Mode getMode() {
		return mode;
	}

	@Override
	public void setMode(Mode mode) {
		Validate.notNull(mode, "Mode can not be null!");
		this.mode = mode;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+mode.ordinal();
	}

}
