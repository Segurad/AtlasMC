package de.atlasmc.event;

import java.util.function.Consumer;

/**
 * Functional interface for event execution
 * @param <E>
 */
@FunctionalInterface
public interface FunctionalListener<E extends Event> extends Listener, Consumer<E> {

}
