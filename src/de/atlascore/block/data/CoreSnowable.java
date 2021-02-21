package de.atlascore.block.data;

import de.atlasmc.Material;
import de.atlasmc.block.data.Snowable;

public class CoreSnowable extends CoreBlockData implements Snowable {

	private boolean snowy;
	
	public CoreSnowable(Material material) {
		super(material);
	}

	@Override
	public boolean isSnowy() {
		return snowy;
	}

	@Override
	public void setSnowy(boolean snowy) {
		this.snowy = snowy;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + (snowy?0:1);
	}
	

}
