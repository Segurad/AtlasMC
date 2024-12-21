package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Leaves;

public class CoreLeaves extends CoreWaterlogged implements Leaves {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterlogged.PROPERTIES, 
				BlockDataProperty.DISTANCE,
				BlockDataProperty.PERSISTENT);
	}
	
	private int distance;
	private boolean persistent;
	
	public CoreLeaves(Material material) {
		super(material);
		distance = 7;
	}

	@Override
	public int getDistance() {
		return distance;
	}

	@Override
	public boolean isPersistent() {
		return persistent;
	}

	@Override
	public void setDistance(int distance) {
		if (distance < 1 || distance > 7) 
			throw new IllegalArgumentException("Distance is not between 1 and 7: " + distance);
		this.distance = distance;
	}
	
	@Override
	public int getMaxDistance() {
		return 7;
	}

	@Override
	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + (distance-1)*4+(persistent?0:2);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
