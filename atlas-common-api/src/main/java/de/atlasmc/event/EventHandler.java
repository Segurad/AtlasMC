package de.atlasmc.event;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.atlasmc.util.annotation.NotNull;

/**
 * Annotation for marking methods of a class implementing the {@link Listener} interface as event handler.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandler {
	
	/**
	 * The priority of execution of this handler
	 * @return priority
	 */
	@NotNull
	EventPriority priority() default EventPriority.NORMAL;

	/**
	 * Whether or not this handler is ignored if the event is cancelled.
	 * @return true if ignore
	 */
	boolean ignoreCancelled() default false;

}
