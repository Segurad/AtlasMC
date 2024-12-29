package de.atlasmc;

import de.atlasmc.chat.Chat;
import de.atlasmc.util.annotation.Nullable;

/**
 * A object that supports a custom name
 */
public interface Nameable {
	
	/**
	 * Returns the custom name or null if non is set
	 * @return name or null
	 */
	@Nullable
	Chat getCustomName();
	
	/**
	 * Sets a custom name or null
	 * @param name
	 */
	void setCustomName(Chat name);
	
	/**
	 * Returns whether or not a custom name is set
	 * @return true if set
	 */
	boolean hasCustomName();

}
