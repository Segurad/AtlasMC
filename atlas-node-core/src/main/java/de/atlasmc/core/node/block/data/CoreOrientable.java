package de.atlasmc.core.node.block.data;

import java.util.List;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.Orientable;
import de.atlasmc.node.block.data.property.BlockDataProperty;

public class CoreOrientable extends CoreBlockData implements Orientable {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.ORIENTATION);
	}
	
	protected Orientation orientation;
	
	public CoreOrientable(BlockType type) {
		super(type);
		orientation = Orientation.NORTH_UP;
	}

	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	@Override
	public void setOrientation(Orientation orientation) {
		if (orientation == null) 
			throw new IllegalArgumentException("Orientation can not be null!");
		this.orientation = orientation;
	}
	
	@Override
	public int getStateID() {
		return type.getBlockStateID()+orientation.ordinal();
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
