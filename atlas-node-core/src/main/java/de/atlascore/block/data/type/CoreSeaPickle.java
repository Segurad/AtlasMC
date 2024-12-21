package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.SeaPickle;

public class CoreSeaPickle extends CoreWaterlogged implements SeaPickle {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterlogged.PROPERTIES, BlockDataProperty.PICKLES);
	}
	
	private int pickles;
	
	public CoreSeaPickle(Material material) {
		super(material);
		pickles = 1;
		setWaterlogged(true);
	}

	@Override
	public int getMaxPickles() {
		return 4;
	}

	@Override
	public int getMinPickles() {
		return 1;
	}

	@Override
	public int getPickles() {
		return pickles;
	}

	@Override
	public void setPickles(int pickles) {
		if (pickles < 1 && pickles > 4) 
			throw new IllegalArgumentException("Pickles is not between 1 and 4: " + pickles);
		this.pickles = pickles;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(pickles-1)*2;
	}

	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
}
