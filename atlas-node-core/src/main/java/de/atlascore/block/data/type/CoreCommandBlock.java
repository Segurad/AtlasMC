package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.CommandBlock;

public class CoreCommandBlock extends CoreDirectional6Faces implements CommandBlock {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional6Faces.PROPERTIES, BlockDataProperty.CONDITIONAL);
	}
	
	private boolean conditional;
	
	public CoreCommandBlock(BlockType type) {
		super(type);
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
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
