package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional6Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Barrel;

public class CoreBarrel extends CoreDirectional6Faces implements Barrel {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional6Faces.PROPERTIES, PropertyType.OPEN);
	}
	
	private boolean open;
	
	public CoreBarrel(BlockType type) {
		super(type);
	}

	@Override
	public boolean isOpen() {
		return open;
	}

	@Override
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID()+
				(open?0:1)+
				getFaceValue()*2;
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
