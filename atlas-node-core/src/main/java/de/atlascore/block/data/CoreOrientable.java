package de.atlascore.block.data;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.Orientable;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreOrientable extends CoreBlockData implements Orientable {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.ORIENTATION);
	}
	
	private Orientation orientation;
	
	public CoreOrientable(Material material) {
		super(material);
		orientation = Orientation.NORTH_UP;
	}

	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	@Override
	public void setOrientation(Orientation orientation) {
		if (orientation == null) 
			throw new IllegalArgumentException("Orientation can not be null!");
		this.orientation = orientation;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+orientation.ordinal();
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
