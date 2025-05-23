package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.TechnicalPiston;

public class CoreTechnicalPiston extends CoreDirectional6Faces implements TechnicalPiston {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional6Faces.PROPERTIES, BlockDataProperty.TYPE);
	}
	
	protected Type pistonType;
	
	public CoreTechnicalPiston(BlockType type) {
		super(type);
		pistonType = Type.NORMAL;
	}

	@Override
	public Type getPistonType() {
		return pistonType;
	}

	@Override
	public void setPistonType(Type type) {
		if (type == null) 
			throw new IllegalArgumentException("Type can not be null!");
		this.pistonType = type;
	}

	@Override
	public int getStateID() {
		return getType().getBlockStateID()+getFaceValue()*2+pistonType.ordinal();
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
