package de.atlasmc.network.chat.placeholder;

import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.Atlas;
import de.atlasmc.network.player.AtlasPlayer;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.ThreadSafe;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.map.key.CharKeyBuffer;

@ThreadSafe
public class PlaceholderHandler {
	
	private static ConcurrentHashMap<CharKey, PlaceholderProvider> PROVIDERS;
	private static final char MARKER = '%';
	
	static {
		PROVIDERS = new ConcurrentHashMap<>();
	}
	
	/**
	 * Registers a provider with {@link PlaceholderProvider#getNamespace()}
	 * If there is a provider with the same name space it will be overwritten 
	 * @param provider to register
	 */
	public static void registerProvider(PlaceholderProvider provider) {
		if (provider == null)
			throw new IllegalArgumentException("Provider can not be null!");
		PROVIDERS.put(CharKey.of(provider.getNamespace()), provider);
		Atlas.getLogger().debug("Placeholder provider registered with namespace: " + provider.getNamespace());
	}

	/**
	 * Unregisters the provider with the given name space
	 * @param namespace to unregister
	 * @return true if unregistered
	 */
	public static boolean unregisterProvider(CharSequence namespace) {
		if (namespace == null)
			throw new IllegalArgumentException("Namespace can not be null!");
		if (PROVIDERS.remove(namespace) != null) {
			Atlas.getLogger().debug("Placeholder provider unregistered with namespace: " + namespace);
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the provider with the given names pace
	 * @param namespace
	 * @return provider or null
	 */
	@Nullable
	public static PlaceholderProvider getProvider(CharSequence namespace) {
		return PROVIDERS.get(namespace);
	}
	
	/**
	 * Sets all placeholders of the given text with the given player
	 * @param text to replace
	 * @param player
	 * @return text with all placeholders set
	 */
	public static String setPlaceholders(String text, AtlasPlayer player) {
		int firstMarker = -1;
		int namespaceEnd = -1;
		int textCopyIndex = 0;
		final char marker = MARKER;
		StringBuilder builder = null;
		CharKeyBuffer keyBuff = null;
		final int textLength = text.length();
		for (int i = 0; i < textLength; i++) {
			char c = text.charAt(i);
			// handle non marker char
			if (c != marker) {
				if (firstMarker != -1) {
					if (c == ' ') { // break placeholder check for current placeholder
						firstMarker = -1;
						namespaceEnd = -1;
					} else if (c == '_' && namespaceEnd == -1) { // end name space
						namespaceEnd = i;
					}
				}
				continue;
			}
			// set first marker if not present 
			if (firstMarker == -1) {
				firstMarker = i;
				continue;
			}
			// handle no namespace found in placeholder
			if (namespaceEnd == -1) {
				String placeholder = text.substring(firstMarker, i+1);
				Atlas.getLogger().debug("Unable to extract namespace from placeholder " + placeholder + " in text: " + text);
				firstMarker = -1;
				continue;
			}
			if (keyBuff == null)
				keyBuff = new CharKeyBuffer();
			keyBuff.clear();
			// get provider
			keyBuff.append(text, firstMarker+1, namespaceEnd-(firstMarker+1));
			PlaceholderProvider provider = getProvider(keyBuff);
			if (provider == null) {
				Atlas.getLogger().debug("Unable to find placeholder provider for namespace " + keyBuff + " in text: " + text);
				firstMarker = -1;
				namespaceEnd = -1;
				continue;
			}
			keyBuff.clear();
			// get replaced text
			keyBuff.append(text, namespaceEnd+1, i-(namespaceEnd+1));
			namespaceEnd = -1;
			CharSequence replacedText = provider.getText(keyBuff, player);
			if (replacedText == null) {
				firstMarker = -1;
				continue;
			}
			if (builder == null)
				builder = new StringBuilder();
			if (textCopyIndex < firstMarker) {
				builder.append(text, textCopyIndex, firstMarker);
			}
			builder.append(replacedText);
			textCopyIndex = i+1;
		}
		if (builder == null)
			return text;
		if (textCopyIndex < textLength) {
			builder.append(text, textCopyIndex, textLength);
		}
		return builder.toString();
	}

}
