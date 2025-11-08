package de.atlasmc.core.node.block.data;

import java.util.List;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.Hatchable;
import de.atlasmc.node.block.data.property.PropertyType;

public class CoreHatchable extends CoreBlockData implements Hatchable {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, PropertyType.HATCH);
	}
	
	private int hatch;
	private final int maxHatch;
	
	public CoreHatchable(BlockType type) {
		this(type, 2);
	}
	
	public CoreHatchable(BlockType type, int maxHatch) {
		super(type);
		this.maxHatch = maxHatch;
	}
	
	@Override
	public void setHatch(int hatch) {
		if (hatch < 0 || hatch > 2) 
			throw new IllegalArgumentException("Hatch is not between 0 and " + maxHatch + ": " + hatch);
		this.hatch = hatch;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + hatch;
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

	@Override
	public int getHatch() {
		return hatch;
	}

	@Override
	public int getMaxHatch() {
		return maxHatch;
	}

}
