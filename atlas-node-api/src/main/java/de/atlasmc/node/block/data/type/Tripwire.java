package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Attachable;
import de.atlasmc.node.block.data.MultipleFacing;
import de.atlasmc.node.block.data.Powerable;

public interface Tripwire extends Attachable, MultipleFacing, Powerable {
	
	boolean isDisarmed();
	
	void setDisarmed(boolean disarmed);

}
