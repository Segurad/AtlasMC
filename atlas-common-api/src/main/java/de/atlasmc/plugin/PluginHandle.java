package de.atlasmc.plugin;

import de.atlasmc.event.Listener;

/**
 * Handle for a {@link Plugin} used for registering features like {@link Listener}s.
 * Allowing to simply group features for a Server or ServerGroup.
 */
public interface PluginHandle {
	
	/**
	 * Returns the plugins this handle belongs to
	 * @return plugin
	 */
	Plugin getPlugin();

}
