package de.atlascore.block.data;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.Rail;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreRail extends CoreWaterlogged implements Rail {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterlogged.PROPERTIES, BlockDataProperty.SHAPE);
	}
	
	private Shape shape;
	
	public CoreRail(BlockType type) {
		super(type);
		shape = Shape.NORTH_SOUTH;
	}

	@Override
	public Shape getShape() {
		return shape;
	}

	@Override
	public void setShape(Shape shape) {
		this.shape = shape;
	}

	@Override
	public Set<Shape> getShapes() {
		return EnumSet.allOf(Shape.class);
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+shape.ordinal()*2;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
