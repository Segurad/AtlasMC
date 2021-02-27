package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.AnaloguePowerable;
import de.atlasmc.util.Validate;

public class CoreAnaloguePowerable extends CoreBlockData implements AnaloguePowerable {

	private int power;
	
	public CoreAnaloguePowerable(Material material) {
		super(material);
	}

	@Override
	public int getMaxPower() {
		return 15;
	}

	@Override
	public int getPower() {
		return power;
	}

	@Override
	public void setPower(int level) {
		Validate.isTrue(level <= 15 && level >= 0, "power is not between 0 and " + 15 + ": " + level);
		this.power = level;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + power;
	}

}
