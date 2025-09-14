package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;

public interface CommandBlock extends Directional {
	
	public boolean isConditional();
	public void setConditional(boolean conditional);

}
