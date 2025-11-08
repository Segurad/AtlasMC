package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional6Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Dispenser;

public class CoreDispenser extends CoreDirectional6Faces implements Dispenser {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional6Faces.PROPERTIES, PropertyType.TRIGGERED);
	}
	
	public boolean triggered;
	
	public CoreDispenser(BlockType type) {
		super(type);
	}

	@Override
	public boolean isTriggered() {
		return triggered;
	}

	@Override
	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
	}

	@Override
	public int getStateID() {
		return getType().getBlockStateID()+getFaceValue(getFacing())*2+(triggered?0:1);
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
