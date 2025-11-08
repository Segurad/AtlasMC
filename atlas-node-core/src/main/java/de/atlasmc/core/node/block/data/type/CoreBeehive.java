package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Beehive;

public class CoreBeehive extends CoreDirectional4Faces implements Beehive {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, PropertyType.HONEY_LEVEL);
	}
	
	private int honeyLevel;
	
	public CoreBeehive(BlockType type) {
		super(type);
	}

	@Override
	public int getHoneyLevel() {
		return honeyLevel;
	}

	@Override
	public int getMaxHoneyLevel() {
		return 5;
	}

	@Override
	public void setHoneyLevel(int honeyLevel) {
		if (honeyLevel >  5 || honeyLevel < 0) 
			throw new IllegalArgumentException("Level is not between 0 and 5: " + honeyLevel);
		this.honeyLevel = honeyLevel;
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID()+
				honeyLevel+
				getFaceValue()*6;
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
