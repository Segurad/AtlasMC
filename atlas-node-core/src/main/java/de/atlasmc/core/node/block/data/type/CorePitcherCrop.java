package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreAgeable;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.PitcherCrop;

public class CorePitcherCrop extends CoreAgeable implements PitcherCrop {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreAgeable.PROPERTIES, PropertyType.HALF);
	}
	
	private Half half;
	
	public CorePitcherCrop(BlockType type) {
		super(type, 4);
		half = Half.LOWER;
	}
	
	@Override
	public CorePitcherCrop clone() {
		return (CorePitcherCrop) super.clone();
	}

	@Override
	public Half getHalf() {
		return half;
	}

	@Override
	public void setHalf(Half half) {
		if (half == null)
			throw new IllegalArgumentException("Half can not be null!");
		if (half == Half.TOP || half == Half.BOTTOM)
			throw new IllegalArgumentException("Invalid half: " + half.name());
		this.half = half;
	}
	
	@Override
	public int getStateID() {
		return type.getBlockStateID() + half.getID() + age*2;
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
