package de.atlasmc.util;

/**
 * Every enum implementing this interface is required to have a static getByID(int) method returning the corresponding enum
 */
public interface EnumID {
	
	int getID();

}
