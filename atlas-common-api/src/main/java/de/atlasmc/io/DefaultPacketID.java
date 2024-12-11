package de.atlasmc.io;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for Packet interfaces
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DefaultPacketID {
	
	/**
	 * 
	 * @return the default packet ID
	 */
	int packetID();
	
	/**
	 * 
	 * @return
	 */
	String definition();

}
