package de.atlasmc.core.node.block.data;

import java.util.List;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.Snowable;
import de.atlasmc.node.block.data.property.PropertyType;

public class CoreSnowable extends CoreBlockData implements Snowable {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, PropertyType.SNOWY);
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
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}
	
}
