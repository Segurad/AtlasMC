package de.atlasmc.registry;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Used to automatically add a instance of this type to the given registry.
 * The type must have a constructor without any arguments.
 */
@Retention(SOURCE)
@Target({ TYPE })
public @interface RegistryValue {
	
	String registry();
	
	String key();
	
	boolean isDefault() default false;

}
