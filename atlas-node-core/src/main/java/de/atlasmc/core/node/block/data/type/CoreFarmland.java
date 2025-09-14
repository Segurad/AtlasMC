package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreBlockData;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.data.type.Farmland;

public class CoreFarmland extends CoreBlockData implements Farmland {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.MOISTURE);
	}
	
	private final int maxmoisture;
	private int moisture;
	
	public CoreFarmland(BlockType type) {
		super(type);
		maxmoisture = 7;
	}

	@Override
	public int getMoisture() {
		return moisture;
	}

	@Override
	public int getMaxMoisture() {
		return maxmoisture;
	}

	@Override
	public void setMoisture(int moisture) {
		if (moisture > maxmoisture || moisture < 0) 
			throw new IllegalArgumentException("Level is not between 0 and " + maxmoisture + ": " + moisture);
		this.moisture = moisture;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
