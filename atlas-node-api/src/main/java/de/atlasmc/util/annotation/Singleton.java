package de.atlasmc.util.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marks a class a singleton
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface Singleton {

}
