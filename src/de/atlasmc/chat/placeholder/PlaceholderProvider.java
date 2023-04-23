package de.atlasmc.chat.placeholder;

import de.atlasmc.atlasnetwork.AtlasPlayer;
import de.atlasmc.util.map.key.CharKey;

public interface PlaceholderProvider {
	
	/**
	 * Returns the namespace of this provider
	 * @return namespace
	 */
	CharSequence getNamespace();
	
	/**
	 * Returns the text associated with the given key and player
	 * @param key
	 * @param player
	 * @return text or null
	 */
	CharSequence getText(CharKey key, AtlasPlayer player);

}
