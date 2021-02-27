package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Scaffolding;
import de.atlasmc.util.Validate;

public class CoreScaffolding extends CoreWaterlogged implements Scaffolding {

	private boolean bottom;
	private int distance;
	
	public CoreScaffolding(Material material) {
		super(material);
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
		Validate.isTrue(distance >= 0 && distance < 8, "Distance is not between 0 and 7: " + distance);
		this.distance = distance;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				distance*2+
				(bottom?0:16);
	}


}
