package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.RespawnAnchor;
import de.atlasmc.util.Validate;

public class CoreRespawnAnchor extends CoreBlockData implements RespawnAnchor {

	private int charges;
	
	public CoreRespawnAnchor(Material material) {
		super(material);
	}

	@Override
	public int getCharges() {
		return charges;
	}

	@Override
	public int getMaxCharges() {
		return 4;
	}

	@Override
	public void setCharges(int charges) {
		Validate.isTrue(charges <=  4 && charges >= 0, "Charges is not between 0 and 4: " + charges);
		this.charges = charges;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+charges;
	}

}
