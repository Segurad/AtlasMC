package de.atlascore.block.data.type;

import de.atlasmc.Material;
import de.atlasmc.block.data.type.PistonHead;

public class CorePistonHead extends CoreTechnicalPiston implements PistonHead {

	private boolean _short;
	
	public CorePistonHead(Material material) {
		super(material);
	}

	@Override
	public boolean isShort() {
		return _short;
	}

	@Override
	public void setShort(boolean _short) {
		this._short = _short;
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+getFaceValue()*4+getType().ordinal()+(_short?0:2);
	}

}
