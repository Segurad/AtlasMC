package de.atlascore.block.data;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.AnaloguePowerable;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreAnaloguePowerable extends CoreBlockData implements AnaloguePowerable {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.POWER);
	}
	
	private int power;
	
	public CoreAnaloguePowerable(Material material) {
		super(material);
	}

	@Override
	public int getMaxPower() {
		return 15;
	}

	@Override
	public int getPower() {
		return power;
	}

	@Override
	public void setPower(int level) {
		if (level > 15 || level < 0) 
			throw new IllegalArgumentException("Power is not between 0 and 15: " + level);
		this.power = level;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + power;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
