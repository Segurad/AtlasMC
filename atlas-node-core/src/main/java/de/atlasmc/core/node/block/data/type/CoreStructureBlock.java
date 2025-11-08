package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreBlockData;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.StructureBlock;

public class CoreStructureBlock extends CoreBlockData implements StructureBlock {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, PropertyType.MODE);
	}
	
	private Mode mode;
	
	public CoreStructureBlock(BlockType type) {
		super(type);
		mode = Mode.LOAD;
	}

	@Override
	public Mode getMode() {
		return mode;
	}

	@Override
	public void setMode(Mode mode) {
		if (mode == null) throw new IllegalArgumentException("Mode can not be null!");
		this.mode = mode;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+mode.ordinal();
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}
	
}
