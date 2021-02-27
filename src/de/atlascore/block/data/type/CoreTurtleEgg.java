package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.TurtleEgg;
import de.atlasmc.util.Validate;

public class CoreTurtleEgg extends CoreBlockData implements TurtleEgg {

	private int eggs, hatch;
	
	public CoreTurtleEgg(Material material) {
		super(material);
	}

	@Override
	public int getEggs() {
		return eggs;
	}

	@Override
	public int getHatch() {
		return hatch;
	}

	@Override
	public int getMaxEggs() {
		return 4;
	}

	@Override
	public int getMaxHatch() {
		return 2;
	}

	@Override
	public int getMinEggs() {
		return 1;
	}

	@Override
	public void setHatch(int hatch) {
		Validate.isTrue(hatch >= 0 && hatch <= 2, "Hatch is not between 0 and 2: " + hatch);
		this.hatch = hatch;
	}

	@Override
	public void setEggs(int eggs) {
		Validate.isTrue(eggs > 0 && eggs <= 4, "Eggs is not between 1 and 4: " + eggs);
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				hatch+(eggs-1)*3;
	}

}
