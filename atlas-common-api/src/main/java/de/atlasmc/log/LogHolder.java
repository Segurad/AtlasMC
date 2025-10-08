package de.atlasmc.log;

import de.atlasmc.util.annotation.NotNull;

public interface LogHolder {

	/**
	 * Returns the logger
	 * @return logger
	 */
	@NotNull
	Log getLogger();
	
}
