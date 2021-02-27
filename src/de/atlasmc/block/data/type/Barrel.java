package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;

public interface Barrel extends Directional {
	
	public boolean isOpen();
	public void setOpen(boolean open);

}
