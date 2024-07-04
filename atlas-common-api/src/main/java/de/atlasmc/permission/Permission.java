package de.atlasmc.permission;

public interface Permission {
	
	/**
	 * Returns the permission key
	 * @return key
	 */
	public String permission();

	/**
	 * Returns the value of this permission.
	 * @return value
	 */
	public int value();

}
