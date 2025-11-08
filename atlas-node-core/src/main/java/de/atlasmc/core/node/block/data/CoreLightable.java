package de.atlasmc.core.node.block.data;

import java.util.List;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.Lightable;
import de.atlasmc.node.block.data.property.PropertyType;

public class CoreLightable extends CoreBlockData implements Lightable {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, PropertyType.LIT);
	}
	
	protected boolean lit;
	
	public CoreLightable(BlockType type) {
		super(type);
	}

	@Override
	public boolean isLit() {
		return lit;
	}

	@Override
	public void setLit(boolean lit) {
		this.lit = lit;
	}
	
	@Override
	public int getStateID() {
		return type.getBlockStateID()+(lit?0:1);
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}
	
}
