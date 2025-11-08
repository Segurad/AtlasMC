package de.atlasmc.core.node.block.data;

import java.util.List;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.AnaloguePowerable;
import de.atlasmc.node.block.data.property.PropertyType;

public class CoreAnaloguePowerable extends CoreBlockData implements AnaloguePowerable {
	
	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, PropertyType.POWER);
	}
	
	protected int power;
	
	public CoreAnaloguePowerable(BlockType type) {
		super(type);
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
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
