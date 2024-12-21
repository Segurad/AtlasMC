package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreWaterloggedDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Chest;

public class CoreChest extends CoreWaterloggedDirectional4Faces implements Chest {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterloggedDirectional4Faces.PROPERTIES, BlockDataProperty.TYPE);
	}
	
	private Type type;
	
	public CoreChest(Material material) {
		super(material);
		type = Type.SINGLE;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public void setType(Type type) {
		if (type == null) 
			throw new IllegalArgumentException("Type can not be null!");
		this.type = type;
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				getFaceValue()*6+
				type.ordinal()*2+
				(isWaterlogged()?0:1);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
