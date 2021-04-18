package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Farmland;
import de.atlasmc.util.Validate;

public class CoreFarmland extends CoreBlockData implements Farmland {

	private final int maxmoisture;
	private int moisture;
	
	public CoreFarmland(Material material) {
		super(material);
		maxmoisture = 7;
	}

	@Override
	public int getMoisture() {
		return moisture;
	}

	@Override
	public int getMaxMoisture() {
		return maxmoisture;
	}

	@Override
	public void setMoisture(int moisture) {
		Validate.isTrue(moisture <= maxmoisture && moisture >= 0, "Level is not between 0 and " + maxmoisture + ": " + moisture);
		this.moisture = moisture;
	}

}