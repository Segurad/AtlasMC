package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Attachable;
import de.atlasmc.block.data.MultipleFacing;
import de.atlasmc.block.data.Powerable;

public interface Tripwire extends Attachable, MultipleFacing, Powerable {
	
	public boolean isDisarmed();
	public void setDisarmed(boolean disarmed);

}
