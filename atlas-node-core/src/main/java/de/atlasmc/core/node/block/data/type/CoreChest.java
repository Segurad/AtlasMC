package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreWaterloggedDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.data.type.Chest;

public class CoreChest extends CoreWaterloggedDirectional4Faces implements Chest {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterloggedDirectional4Faces.PROPERTIES, BlockDataProperty.TYPE);
	}
	
	private Type chestType;
	
	public CoreChest(BlockType type) {
		super(type);
		this.chestType = Type.SINGLE;
	}

	@Override
	public Type getChestType() {
		return chestType;
	}

	@Override
	public void setChestType(Type type) {
		if (type == null) 
			throw new IllegalArgumentException("Type can not be null!");
		this.chestType = type;
	}

	@Override
	public int getStateID() {
		return type.getBlockStateID()+
				getFaceValue()*6+
				chestType.ordinal()*2+
				(waterlogged?0:1);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
