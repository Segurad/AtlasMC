package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.data.type.SmallDripleaf;

public class CoreSmallDripleaf extends CoreDirectional4Faces implements SmallDripleaf {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, 
				BlockDataProperty.WATERLOGGED,
				BlockDataProperty.HALF);
	}
	
	private Half half;
	private boolean waterlogged;
	
	public CoreSmallDripleaf(BlockType type) {
		super(type);
		half = Half.LOWER;
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
	public boolean isWaterlogged() {
		return waterlogged;
	}

	@Override
	public void setWaterlogged(boolean waterlogged) {
		this.waterlogged = waterlogged;
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID() + (waterlogged?0:1) + half.getID()*2 + getFaceValue()*4;
	}
	
	@Override
	public CoreSmallDripleaf clone() {
		return (CoreSmallDripleaf) super.clone();
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
