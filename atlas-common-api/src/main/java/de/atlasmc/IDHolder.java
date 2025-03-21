package de.atlasmc;

/**
 * Represents a object that can be identified by a numeric id
 */
public interface IDHolder {
	
	/**
	 * Returns the id or -1 if no id set
	 * @return id or -1
	 */
	int getID();

}
