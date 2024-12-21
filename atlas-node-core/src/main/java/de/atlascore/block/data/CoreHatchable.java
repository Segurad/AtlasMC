package de.atlascore.block.data;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.Hatchable;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreHatchable extends CoreBlockData implements Hatchable {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.HATCH);
	}
	
	private int hatch;
	private final int maxHatch;
	
	public CoreHatchable(Material material) {
		this(material, 2);
	}
	
	public CoreHatchable(Material mater, int maxHatch) {
		super(mater);
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
	public List<BlockDataProperty<?>> getProperties() {
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
