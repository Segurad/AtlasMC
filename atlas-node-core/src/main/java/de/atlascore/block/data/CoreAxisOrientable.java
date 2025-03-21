package de.atlascore.block.data;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import de.atlasmc.Axis;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.AxisOrientable;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreAxisOrientable extends CoreBlockData implements AxisOrientable {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.AXIS);
	}
	
	private static final Set<Axis> ALLOWED_AXIS =
			EnumSet.allOf(Axis.class);
	
	protected Axis axis;
	
	public CoreAxisOrientable(BlockType type) {
		this(type, Axis.Y);
	}
	
	public CoreAxisOrientable(BlockType type, Axis axis) {
		super(type);
		setAxis(axis);
	}

	@Override
	public Set<Axis> getAxes() {
		return ALLOWED_AXIS;
	}

	@Override
	public Axis getAxis() {
		return axis;
	}

	@Override
	public void setAxis(Axis axis) {
		if (axis == null)
			throw new IllegalArgumentException("Axis can not be null!");
		if (!getAxes().contains(axis))
			throw new IllegalArgumentException("Axis is not valid: " + axis.name());
		this.axis = axis;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+axis.ordinal();
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
