package de.atlasmc.plugin;

import de.atlasmc.event.Listener;
import de.atlasmc.util.annotation.NotNull;

/**
 * Handle for a {@link Plugin} used for registering features like {@link Listener}s.
 * Allowing to simply group features for a Server or ServerGroup.
 */
public interface PluginHandle {
	
	/**
	 * Returns the plugins this handle belongs to
	 * @return plugin
	 */
	@NotNull
	Plugin getPlugin();

}
