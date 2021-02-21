package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.AnaloguePowerable;
import de.atlasmc.util.Validate;

public class CoreAnaloguePowerable extends CoreBlockData implements AnaloguePowerable {

	private int maxpower, power;
	
	public CoreAnaloguePowerable(Material material) {
		super(material);
		maxpower = 15;
	}

	@Override
	public int getMaxPower() {
		return maxpower;
	}

	@Override
	public int getPower() {
		return power;
	}

	@Override
	public void setPower(int level) {
		Validate.isTrue(level <= maxpower && level >= 0, "power is not between 0 and " + maxpower + ": " + level);
		this.power = level;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + power;
	}

}
