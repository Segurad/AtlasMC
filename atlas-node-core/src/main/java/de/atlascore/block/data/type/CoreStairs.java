package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Stairs;

public class CoreStairs extends CoreDirectional4Faces implements Stairs {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, 
				BlockDataProperty.WATERLOGGED,
				BlockDataProperty.HALF,
				BlockDataProperty.SHAPE);
	}
	
	private Half half;
	private boolean waterlogged;
	private Shape shape;
	
	public CoreStairs(Material material) {
		super(material);
		this.half = Half.BOTTOM;
		this.shape = Shape.STRAIGHT;
	}
	
	@Override
	public CoreStairs clone() {
		return (CoreStairs) super.clone();
	}

	@Override
	public Half getHalf() {
		return half;
	}

	@Override
	public void setHalf(Half half) {
		if (half == null) 
			throw new IllegalArgumentException("Half can not be null!");
		if (half == Half.LOWER || half == Half.UPPER)
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
	public Shape getShape() {
		return shape;
	}

	@Override
	public void setShape(Shape shape) {
		if (shape == null) 
			throw new IllegalArgumentException("Shape can not be null!");
		this.shape = shape;
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				(waterlogged?0:1)+
				shape.ordinal()*2+
				half.getID()*10+
				getFaceValue()*20;
	}

	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
}
