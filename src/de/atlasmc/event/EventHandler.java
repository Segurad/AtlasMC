package de.atlasmc.event;

public @interface EventHandler {

	EventPriority priority();
	
	public enum EventPriority {
		HIGHEST
		
	}

	boolean ignoreCancelled();

}
