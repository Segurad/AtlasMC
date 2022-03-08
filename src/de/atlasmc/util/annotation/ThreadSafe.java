package de.atlasmc.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Types, Methods, Fields and Constructors marked with this annotation should be assumed and implemented as ThreadSafe.<br>
 * If a Type is marked as ThreadSafe all Methods Fields and Constructors should be treated as ThreadSafe
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
public @interface ThreadSafe {

}
