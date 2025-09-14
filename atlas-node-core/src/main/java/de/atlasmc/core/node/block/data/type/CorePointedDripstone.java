package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreWaterlogged;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.data.type.PointedDripstone;

public class CorePointedDripstone extends CoreWaterlogged implements PointedDripstone {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterlogged.PROPERTIES, 
				BlockDataProperty.THICKNESS,
				BlockDataProperty.VERTICAL_DIRECTION);
	}
	
	private Thickness thickness;
	private VerticalDirection direction;
	
	public CorePointedDripstone(BlockType type) {
		super(type);
		thickness = Thickness.TIP;
		direction = VerticalDirection.UP;
	}

	@Override
	public Thickness getThickness() {
		return thickness;
	}

	@Override
	public void setThickness(Thickness thickness) {
		if (thickness == null)
			throw new IllegalArgumentException("Thickness can not be null!");
		this.thickness = thickness;
	}

	@Override
	public VerticalDirection getDirection() {
		return direction;
	}

	@Override
	public void setDirection(VerticalDirection direction) {
		if (direction == null)
			throw new IllegalArgumentException("Direction can not be null!");
		this.direction = direction;
	}

	@Override
	public int getStateID() {
		return super.getStateID() + direction.ordinal()*2 + thickness.ordinal()*4;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
	@Override
	public CorePointedDripstone clone() {
		return (CorePointedDripstone) super.clone();
	}

}
