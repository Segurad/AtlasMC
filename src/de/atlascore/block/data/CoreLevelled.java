package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.Levelled;
import de.atlasmc.util.Validate;

public class CoreLevelled extends CoreBlockData implements Levelled {

	private int level;
	private final int maxlevel;
	
	public CoreLevelled(Material material) {
		super(material);
		maxlevel = 15;
	}

	@Override
	public int getMaxLevel() {
		return maxlevel;
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public void setLevel(int level) {
		Validate.isTrue(level <= maxlevel && level >= 0, "Level is not between 0 and " + maxlevel + ": " + level);
		this.level = level;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + level;
	}

}
