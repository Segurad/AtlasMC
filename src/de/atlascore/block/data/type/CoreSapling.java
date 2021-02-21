package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Sapling;
import de.atlasmc.util.Validate;

public class CoreSapling extends CoreBlockData implements Sapling {

	private int stage;
	private int maxstage;
	
	public CoreSapling(Material material) {
		super(material);
		maxstage = 2;
	}

	@Override
	public int getMaxStage() {
		return maxstage;
	}

	@Override
	public int getStage() {
		return stage;
	}

	@Override
	public void setStage(int stage) {
		Validate.isTrue(maxstage >= stage && stage >= 0, "Stage is not between 0 and " + maxstage + ": " + stage);
		this.stage = stage;
	}

	@Override
	public int getStateID() {
		return super.getStateID() + stage;
	}
	
}
