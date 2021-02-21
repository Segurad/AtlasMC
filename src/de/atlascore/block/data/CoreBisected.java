package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.Bisected;
import de.atlasmc.util.Validate;

public class CoreBisected extends CoreBlockData implements Bisected {

	private Half half;
	
	public CoreBisected(Material material) {
		super(material);
		this.half = Half.BOTTOM;
	}

	@Override
	public Half getHalf() {
		return half;
	}

	@Override
	public void setHalf(Half half) {
		Validate.notNull(half, "Half can not be null!");
		this.half = half;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+half.ordinal();
	}

}
