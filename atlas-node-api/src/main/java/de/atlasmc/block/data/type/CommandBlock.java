package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;

public interface CommandBlock extends Directional {
	
	public boolean isConditional();
	public void setConditional(boolean conditional);

}
