package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Beehive;
import de.atlasmc.util.Validate;

public class CoreBeehive extends CoreDirectional4Faces implements Beehive {

	private int honeyLevel;
	
	public CoreBeehive(Material material) {
		super(material);
	}

	@Override
	public int getHoneyLevel() {
		return honeyLevel;
	}

	@Override
	public int getMaxHoneyLevel() {
		return 5;
	}

	@Override
	public void setHoneyLevel(int honeyLevel) {
		Validate.isTrue(honeyLevel <=  5 && honeyLevel >= 0, "Level is not between 0 and 5: " + honeyLevel);
		this.honeyLevel = honeyLevel;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				honeyLevel+
				getFaceValue()*6;
	}

}
