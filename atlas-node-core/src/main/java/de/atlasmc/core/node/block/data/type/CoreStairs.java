package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Stairs;

public class CoreStairs extends CoreDirectional4Faces implements Stairs {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, 
				PropertyType.WATERLOGGED,
				PropertyType.HALF,
				PropertyType.SHAPE);
	}
	
	private Half half;
	private boolean waterlogged;
	private Shape shape;
	
	public CoreStairs(BlockType type) {
		super(type);
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
		return getType().getBlockStateID()+
				(waterlogged?0:1)+
				shape.ordinal()*2+
				half.getID()*10+
				getFaceValue()*20;
	}

	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}
	
}
