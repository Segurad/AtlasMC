package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional6Faces;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Observer;

public class CoreObserver extends CoreDirectional6Faces implements Observer {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional6Faces.PROPERTIES, PropertyType.POWERED);
	}
	
	private boolean powered;
	
	public CoreObserver(BlockType type) {
		super(type, BlockFace.SOUTH);
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
