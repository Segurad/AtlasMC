package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.type.Jukebox;

public class CoreJukebox extends CoreBlockData implements Jukebox {

	private boolean has;
	
	public CoreJukebox(Material material) {
		super(material);
	}

	@Override
	public boolean hasRecord() {
		return has;
	}

	@Override
	public void setRecord(boolean has) {
		this.has = has;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(has?0:1);
	}

}
