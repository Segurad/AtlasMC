package de.atlascore.block.data;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.Bisected;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreBisected extends CoreBlockData implements Bisected {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.HALF);
	}
	
	private Half half;
	
	public CoreBisected(Material material) {
		super(material);
		this.half = Half.BOTTOM;
	}

	@Override
	public CoreBisected clone() {
		return (CoreBisected) super.clone();
	}
	
	@Override
	public Half getHalf() {
		return half;
	}

	@Override
	public void setHalf(Half half) {
		if (half == null) 
			throw new IllegalArgumentException("Half can not be null!");
		if (half == Half.LOWER || half == Half.UPPER)
			throw new IllegalArgumentException("Invalid half: " + half.name());
		this.half = half;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+half.getID();
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
