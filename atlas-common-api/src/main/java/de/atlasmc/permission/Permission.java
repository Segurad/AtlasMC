package de.atlasmc.permission;

import de.atlasmc.util.annotation.NotNull;

public interface Permission {
	
	/**
	 * Returns the permission key
	 * @return key
	 */
	@NotNull
	String permission();

	/**
	 * Returns the value of this permission.
	 * @return value
	 */
	int value();

}
