package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreAnaloguePowerable;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.DaylightDetectore;

public class CoreDaylightDetector extends CoreAnaloguePowerable implements DaylightDetectore {

	private boolean inverted;
	
	public CoreDaylightDetector(Material material) {
		super(material);
	}

	@Override
	public boolean isInverted() {
		return inverted;
	}

	@Override
	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				(inverted?0:16);
	}

}
