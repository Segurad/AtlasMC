package de.atlasmc.registry;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import de.atlasmc.NamespacedKey;

/**
 * Used to automatically add a instance of this type to the given registry.
 * The type must have a constructor without any arguments.
 */
@Retention(SOURCE)
@Target({ TYPE })
public @interface RegistryValue {
	
	/**
	 * Defines the key of the registry that should be used for this value.
	 * The registry key must be a valid {@link NamespacedKey}
	 * @return registry key
	 */
	String registry();
	
	/**
	 * Defines the key of this value within the given registry
	 * The key must be a valid {@link NamespacedKey}
	 * @return key
	 */
	String key();
	
	/**
	 * Whether or not this value should be set a default value for the given registry
	 * @return true if default
	 */
	boolean isDefault() default false;

}
