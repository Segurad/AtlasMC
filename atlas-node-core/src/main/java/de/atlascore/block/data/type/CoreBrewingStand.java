package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.BrewingStand;

public class CoreBrewingStand extends CoreBlockData implements BrewingStand {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, 
				BlockDataProperty.HAS_BOTTLE_0,
				BlockDataProperty.HAS_BOTTLE_1,
				BlockDataProperty.HAS_BOOTLE_2);
	}
	
	private boolean[] bottles;
	
	public CoreBrewingStand(Material material) {
		super(material);
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
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
