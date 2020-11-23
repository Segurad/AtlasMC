package de.atlasmc.event;

public interface Cancellable {

	public void setCancelled(boolean cancelled);
	public boolean isCancelled();
}
