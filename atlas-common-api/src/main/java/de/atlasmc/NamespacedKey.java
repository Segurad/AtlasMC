package de.atlasmc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.annotation.NotNull;

public class NamespacedKey {
	
	public static final Pattern NAMESPACE_PATTERN = Pattern.compile("^[a-z0-9\\.\\-_]+$");
	public static final Pattern KEY_PATTERN = Pattern.compile("^[a-z0-9\\.\\-_\\/]+$");
	public static final Pattern NAMESPACED_KEY_PATTERN = Pattern.compile("^[a-z0-9\\.\\-_]+:[a-z0-9\\.\\-_\\/]+$");
	
	public static final String MINECRAFT = "minecraft";
	public static final String ATLAS = "atlas";
	
	private static final Map<String, NamespacedKey> CACHE;
	
	static {
		String rawCacheSize = System.getProperty("de.atlasmc.namespacedkey.literalCacheSize");
		int cacheSize = NumberConversion.toInt(rawCacheSize, 512);
		CACHE = new ConcurrentHashMap<>(cacheSize);
	}
	
	private final String key;
	private final String namespace;
	private final String combination;
	
	private NamespacedKey(String namespace, String key, String namespacedKey) {
		this.key = key;
		this.namespace = namespace.intern();
		this.combination = namespacedKey;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getNamespace() {
		return namespace;
	}
	
	@Override
	public String toString() {
		return combination;
	}
	
	public static interface Namespaced {
		
		/**
		 * Returns the key
		 * @return key
		 */
		@NotNull
		public default String getKey() {
			return getNamespacedKey().getKey();
		}
		
		/**
		 * Returns the namespace
		 * @return namespace
		 */
		@NotNull
		public default String getNamespace() {
			return getNamespacedKey().getNamespace();
		}
		
		/**
		 * Returns the {@link NamespacedKey} as String
		 * @return namespaced key
		 */
		@NotNull
		public default String getNamespacedKeyRaw() {
			return getNamespacedKey().toString();
		}
		
		/**
		 * Returns the {@link NamespacedKey} of this Object
		 * @return key
		 */
		@NotNull
		NamespacedKey getNamespacedKey();
		
		/**
		 * Returns the {@link NamespacedKey} used client side of this Object
		 * @return key
		 */
		default NamespacedKey getClientKey() {
			return getNamespacedKey();
		}
		
	}

	@Override
	public int hashCode() {
		return combination.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NamespacedKey other = (NamespacedKey) obj;
		if (combination == null) {
			if (other.combination != null)
				return false;
		} else if (!combination.equals(other.combination))
			return false;
		return true;
	}

	public static NamespacedKey of(CharSequence namespacedKey) {
		if (namespacedKey == null)
			throw new IllegalArgumentException("NamespacedKey can not be null!");
		NamespacedKey k = CACHE.get(namespacedKey);
		if (k != null)
			return k;
		String rawKey = namespacedKey.toString();
		int first = rawKey.indexOf(':');
		if (first < 1 || !NAMESPACED_KEY_PATTERN.matcher(namespacedKey).matches())
			throw new IllegalArgumentException("Invalid namespaced key: " + namespacedKey);
		String namespace = rawKey.substring(0, first);
		String key = rawKey.substring(first+1);
		return new NamespacedKey(namespace, key, rawKey);
	}
	
	public static NamespacedKey of(String namespace, String key) {
		if (namespace == null) 
			throw new IllegalArgumentException("Namespace can not be null!");
		if (key == null) 
			throw new IllegalArgumentException("Name can not be null!");
		String namespacedKey = namespace + ":" + key;
		NamespacedKey k = CACHE.get(namespacedKey);
		if (k != null)
			return k;
		if (!NAMESPACED_KEY_PATTERN.matcher(namespacedKey).matches())
			throw new IllegalArgumentException("Invalid namespaced key: " + namespacedKey);
		return new NamespacedKey(namespace, key, namespacedKey);
	}
	
	@NotNull
	public static NamespacedKey literal(String namespacedKey) {
		if (namespacedKey == null)
			throw new IllegalArgumentException("NamespacedKey can not be null!");
		NamespacedKey k = CACHE.get(namespacedKey);
		if (k != null)
			return k;
		k = of(namespacedKey);
		NamespacedKey cacheKey = CACHE.putIfAbsent(namespacedKey, k);
		if (cacheKey != null)
			k = cacheKey;
		return k;
	}
	
	public static void clearLiteralCache() {
		CACHE.clear();
	}

}
