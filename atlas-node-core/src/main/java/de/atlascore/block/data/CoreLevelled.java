package de.atlascore.block.data;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.Levelled;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreLevelled extends CoreBlockData implements Levelled {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.LEVEL);
	}
	
	private int level;
	private final int maxlevel;
	
	public CoreLevelled(Material material) {
		this(material, 15);
	}
	
	public CoreLevelled(Material material, int maxlevel) {
		super(material);
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
