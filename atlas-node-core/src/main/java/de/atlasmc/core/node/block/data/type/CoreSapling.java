package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreBlockData;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Sapling;

public class CoreSapling extends CoreBlockData implements Sapling {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, PropertyType.STAGE);
	}
	
	private int stage;
	private int maxstage;
	
	public CoreSapling(BlockType type) {
		this(type, 1);
	}
	
	public CoreSapling(BlockType type, int maxstage) {
		super(type);
		this.maxstage = maxstage;
	}

	@Override
	public int getMaxStage() {
		return maxstage;
	}

	@Override
	public int getStage() {
		return stage;
	}

	@Override
	public void setStage(int stage) {
		if (maxstage < stage || stage < 0) 
			throw new IllegalArgumentException("Stage is not between 0 and " + maxstage + ": " + stage);
		this.stage = stage;
	}

	@Override
	public int getStateID() {
		return super.getStateID() + stage;
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}
	
}
