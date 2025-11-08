package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreAgeable;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.CaveVines;

public class CoreCaveVines extends CoreAgeable implements CaveVines {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreAgeable.PROPERTIES, PropertyType.BERRIES);
	}
	
	private boolean berries;
	
	public CoreCaveVines(BlockType type) {
		super(type, 25);
	}

	@Override
	public boolean hasBerries() {
		return berries;
	}

	@Override
	public void setBerries(boolean berries) {
		this.berries = berries;
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID()+(berries?0:1)+getAge()*2;
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
