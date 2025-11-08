package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional6Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Piston;

public class CorePiston extends CoreDirectional6Faces implements Piston {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional6Faces.PROPERTIES, PropertyType.EXTENDED);
	}
	
	private boolean extended;
	
	public CorePiston(BlockType type) {
		super(type);
	}

	@Override
	public boolean isExtended() {
		return extended;
	}

	@Override
	public void setExtended(boolean extended) {
		this.extended = extended;
	}

	@Override
	public int getStateID() {
		return type.getBlockStateID()+getFaceValue()+(extended?0:6);
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
