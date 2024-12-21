package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Attachable;
import de.atlasmc.block.data.MultipleFacing;
import de.atlasmc.block.data.Powerable;

public interface Tripwire extends Attachable, MultipleFacing, Powerable {
	
	boolean isDisarmed();
	
	void setDisarmed(boolean disarmed);

}
