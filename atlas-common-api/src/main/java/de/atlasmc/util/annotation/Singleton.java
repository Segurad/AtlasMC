package de.atlasmc.util.annotation;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class a singleton.
 * Types marked with this annotation should contain a static function getInstance()
 */
@Retention(RetentionPolicy.SOURCE)
@Target(TYPE)
public @interface Singleton {

}
