package de.atlascore.block.data.type;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import de.atlascore.block.data.CoreRail;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.RedstoneRail;

public class CoreRedstoneRail extends CoreRail implements RedstoneRail {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreRail.PROPERTIES, BlockDataProperty.POWERED);
	}
	
	private boolean powered;
	
	public CoreRedstoneRail(Material material) {
		super(material);
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
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
