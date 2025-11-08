package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreBlockData;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.BrewingStand;

public class CoreBrewingStand extends CoreBlockData implements BrewingStand {
	
	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, 
				PropertyType.HAS_BOTTLE_0,
				PropertyType.HAS_BOTTLE_1,
				PropertyType.HAS_BOOTLE_2);
	}
	
	private boolean[] bottles;
	
	public CoreBrewingStand(BlockType type) {
		super(type);
		bottles = new boolean[3];
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				(bottles[0]?0:1)+
				(bottles[1]?0:2)+
				(bottles[2]?0:4);
	}
	
	@Override
	public void setBottle(int slot, boolean has) {
		bottles[slot] = has;
	}

	@Override
	public boolean hasBottle(int slot) {
		return bottles[slot];
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
