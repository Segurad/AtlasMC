package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.PistonHead;

public class CorePistonHead extends CoreTechnicalPiston implements PistonHead {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreTechnicalPiston.PROPERTIES, PropertyType.SHORT);
	}
	
	private boolean isShort;
	
	public CorePistonHead(BlockType type) {
		super(type);
	}

	@Override
	public boolean isShort() {
		return isShort;
	}

	@Override
	public void setShort(boolean isShort) {
		this.isShort = isShort;
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID()+getFaceValue()*4+pistonType.ordinal()+(isShort?0:2);
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
