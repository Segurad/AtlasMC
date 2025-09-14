package de.atlasmc.core.node.block.data;

import java.util.List;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.Levelled;
import de.atlasmc.node.block.data.property.BlockDataProperty;

public class CoreLevelled extends CoreBlockData implements Levelled {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.LEVEL);
	}
	
	protected int level;
	protected final int maxlevel;
	
	public CoreLevelled(BlockType type) {
		this(type, 15);
	}
	
	public CoreLevelled(BlockType type, int maxlevel) {
		super(type);
		this.maxlevel = maxlevel;
	}

	@Override
	public int getMaxLevel() {
		return maxlevel;
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public void setLevel(int level) {
		if (level > maxlevel || level < 0) 
			throw new IllegalArgumentException("Level is not between 0 and " + maxlevel + ": " + level);
		this.level = level;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + level;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
