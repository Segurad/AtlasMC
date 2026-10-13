package de.atlasmc.event;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import de.atlasmc.plugin.Plugin;

/**
 * Annotation for marking a {@link Listener} class for auto registration.
 * The marked class must implement a constructor without any other arguments.
 * The Listener is registered using the {@link Plugin} itself.
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface ListenerAutoregister {
	
	/**
	 * Whether or not this listener should be treated as default listener providing default {@link EventExecutor}
	 * @return true if default
	 */
	boolean isDefault() default false;

}
