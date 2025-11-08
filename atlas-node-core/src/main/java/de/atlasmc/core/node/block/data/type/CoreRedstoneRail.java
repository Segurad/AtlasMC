package de.atlasmc.core.node.block.data.type;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import de.atlasmc.core.node.block.data.CoreRail;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.RedstoneRail;

public class CoreRedstoneRail extends CoreRail implements RedstoneRail {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreRail.PROPERTIES, PropertyType.POWERED);
	}
	
	private boolean powered;
	
	public CoreRedstoneRail(BlockType type) {
		super(type);
	}

	@Override
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}
	
	@Override
	public void setShape(Shape shape) {
		if (shape.ordinal() > 6) throw new IllegalArgumentException("No valid Shape: " + shape.name());
		super.setShape(shape);
	}
	
	@Override
	public Set<Shape> getShapes() {
		return EnumSet.range(Shape.NORTH_SOUTH, Shape.ASCENTING_SOUTH);
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(powered?0:12);
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
