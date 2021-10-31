package de.atlasmc.event;

/**
 * Functional interface for event execution
 * @param <E>
 */
@FunctionalInterface
public interface FunctionalListener<E extends Event> extends Listener {
	
	public void fireEvent(E event);

}
