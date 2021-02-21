package de.atlasmc.block.data;

public interface Dispenser extends Directional {
	
	public boolean isTriggered();
	public void setTriggered(boolean triggered);

}
