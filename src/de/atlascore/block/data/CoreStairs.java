package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.Stairs;
import de.atlasmc.util.Validate;

public class CoreStairs extends CoreAbstractDirectional4Faces implements Stairs {

	private Half half;
	private boolean waterlogged;
	private Shape shape;
	
	public CoreStairs(Material material) {
		super(material);
		this.half = Half.TOP;
		this.shape = Shape.STRAIGHT;
	}

	@Override
	public Half getHalf() {
		return half;
	}

	@Override
	public void setHalf(Half half) {
		Validate.notNull(half, "Half can not be null!");
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
		Validate.notNull(shape, "Shape can not be null!");
		this.shape = shape;
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(waterlogged?0:1)+
				shape.ordinal()*2+
				half.ordinal()*10+
				getFaceValue()*20;
	}

}
