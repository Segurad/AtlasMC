package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.WallSkull;

public class CoreWallSkull extends CoreDirectional4Faces implements WallSkull {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, PropertyType.POWERED);
	}
	
	private boolean powered;
	
	public CoreWallSkull(BlockType type) {
		super(type);
	}

	@Override
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID()+
				(powered?0:1)+
				getFaceValue()*2;
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
