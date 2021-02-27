package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Cake;
import de.atlasmc.util.Validate;

public class CoreCake extends CoreBlockData implements Cake {

	private int bites;
	
	public CoreCake(Material material) {
		super(material);
	}

	@Override
	public int getBites() {
		return bites;
	}

	@Override
	public int getMaxBites() {
		return 6;
	}

	@Override
	public void setBites(int bites) {
		Validate.isTrue(bites >= 0 && bites < 7, "Bites is not between 0 and 6: " + bites);
		this.bites = bites;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+bites;
	}

}
