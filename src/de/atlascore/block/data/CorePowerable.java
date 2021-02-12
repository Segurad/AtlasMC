package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.Powerable;

public class CorePowerable extends CoreBlockData implements Powerable {

	private boolean powered;
	
	public CorePowerable(Material material) {
		super(material);
	}

	@Override
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(powered?0:1);
	}

}
