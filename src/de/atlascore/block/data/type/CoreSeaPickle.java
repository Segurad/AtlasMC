package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.SeaPickle;
import de.atlasmc.util.Validate;

public class CoreSeaPickle extends CoreWaterlogged implements SeaPickle {

	private int pickles;
	
	public CoreSeaPickle(Material material) {
		super(material);
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
		Validate.isTrue(pickles > 0 && pickles <= 4, "Pickles is not between 1 and 4: " + pickles);
		this.pickles = pickles;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(pickles-1)*2;
	}

}
