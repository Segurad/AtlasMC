package de.atlascore.block.data;

import java.util.List;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.Snowable;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreSnowable extends CoreBlockData implements Snowable {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.SNOWY);
	}
	
	private boolean snowy;
	
	public CoreSnowable(BlockType type) {
		super(type);
	}

	@Override
	public boolean isSnowy() {
		return snowy;
	}

	@Override
	public void setSnowy(boolean snowy) {
		this.snowy = snowy;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + (snowy?0:1);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
}
