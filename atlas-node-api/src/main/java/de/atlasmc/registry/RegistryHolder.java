package de.atlasmc.registry;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to automatically created a registry of the type this annotation is used on
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ TYPE })
public @interface RegistryHolder {
	
	String key();

}
