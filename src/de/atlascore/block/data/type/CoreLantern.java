package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Lantern;

public class CoreLantern extends CoreWaterlogged implements Lantern {

	private boolean hanging;
	
	public CoreLantern(Material material) {
		super(material);
	}

	@Override
	public boolean isHanging() {
		return hanging;
	}

	@Override
	public void setHanding(boolean hanging) {
		this.hanging = hanging;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				(hanging?0:2);
	}

}
