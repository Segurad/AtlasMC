package de.atlascore.block.data.type;

import java.util.EnumSet;
import java.util.Set;

import de.atlascore.block.data.CoreRail;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.RedstoneRail;
import de.atlasmc.util.Validate;

public class CoreRedstoneRail extends CoreRail implements RedstoneRail {

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
		Validate.isTrue(shape.ordinal() < 6, "No valid Shape: " + shape.name());
		super.setShape(shape);
	}
	
	@Override
	public Set<Shape> getShapes() {
		return EnumSet.range(Shape.NORTH_SOUTH, Shape.ASCENTING_SOUTH);
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(powered?0:6);
	}

}
