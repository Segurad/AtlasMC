package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional6Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.CommandBlock;

public class CoreCommandBlock extends CoreDirectional6Faces implements CommandBlock {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional6Faces.PROPERTIES, PropertyType.CONDITIONAL);
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
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
