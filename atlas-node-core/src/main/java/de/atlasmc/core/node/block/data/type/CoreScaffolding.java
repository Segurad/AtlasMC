package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreWaterlogged;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Scaffolding;

public class CoreScaffolding extends CoreWaterlogged implements Scaffolding {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterlogged.PROPERTIES, 
				PropertyType.BOTTOM,
				PropertyType.DISTANCE);
	}
	
	private boolean bottom;
	private int distance;
	
	public CoreScaffolding(BlockType type) {
		super(type);
		distance = 7;
	}

	@Override
	public int getDistance() {
		return distance;
	}

	@Override
	public int getMaxDistance() {
		return 7;
	}

	@Override
	public boolean isBottom() {
		return bottom;
	}

	@Override
	public void setBottom(boolean bottom) {
		this.bottom = bottom;
	}

	@Override
	public void setDistance(int distance) {
		if (distance < 0 && distance > 7) 
			throw new IllegalArgumentException("Distance is not between 0 and 7: " + distance);
		this.distance = distance;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				distance*2+
				(bottom?0:16);
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
