package de.atlasmc.util;

/**
 * Every enum implementing this interface is required to have a static getByName(String) method returning the corresponding enum
 */
public interface EnumName {

	String getName();
	
}
