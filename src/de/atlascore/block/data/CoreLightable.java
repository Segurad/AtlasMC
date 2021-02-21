package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.Lightable;

public class CoreLightable extends CoreBlockData implements Lightable {

	private boolean lit;
	
	public CoreLightable(Material material) {
		super(material);
	}

	@Override
	public boolean isLit() {
		return lit;
	}

	@Override
	public void setLit(boolean lit) {
		this.lit = lit;
	}

}
