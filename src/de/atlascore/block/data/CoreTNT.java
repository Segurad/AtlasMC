package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.TNT;

public class CoreTNT extends CoreBlockData implements TNT {

	private boolean stable;
	
	public CoreTNT(Material material) {
		super(material);
	}

	@Override
	public boolean isStable() {
		return stable;
	}

	@Override
	public void setStable(boolean stable) {
		this.stable = stable;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(stable?0:1);
	}

}
