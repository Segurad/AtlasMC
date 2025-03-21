package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreAgeable;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.PitcherCrop;

public class CorePitcherCrop extends CoreAgeable implements PitcherCrop {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreAgeable.PROPERTIES, BlockDataProperty.HALF);
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
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
