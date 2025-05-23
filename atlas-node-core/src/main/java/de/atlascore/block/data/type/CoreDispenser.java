package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreDirectional6Faces;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Dispenser;

public class CoreDispenser extends CoreDirectional6Faces implements Dispenser {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional6Faces.PROPERTIES, BlockDataProperty.TRIGGERED);
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
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
