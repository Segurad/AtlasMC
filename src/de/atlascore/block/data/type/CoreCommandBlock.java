package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.CommandBlock;

public class CoreCommandBlock extends CoreDirectional6Faces implements CommandBlock {

	private boolean conditional;
	
	public CoreCommandBlock(Material material) {
		super(material);
	}

	@Override
	public boolean isConditional() {
		return conditional;
	}

	@Override
	public void setConditional(boolean conditional) {
		this.conditional = conditional;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(conditional?0:6);
	}

}
