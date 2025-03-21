package de.atlasmc.event;

public interface Cancellable {

	void setCancelled(boolean cancelled);
	
	boolean isCancelled();
	
}
