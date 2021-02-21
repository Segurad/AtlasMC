package de.atlascore.block.data;

import java.util.EnumSet;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.data.Rail;

public class CoreRail extends CoreBlockData implements Rail {

	private Shape shape;
	
	public CoreRail(Material material) {
		super(material);
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
		return super.getStateID()+shape.ordinal();
	}

}
