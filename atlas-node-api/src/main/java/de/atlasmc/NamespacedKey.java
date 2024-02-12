package de.atlasmc;

import java.util.regex.Pattern;

import de.atlasmc.util.annotation.NotNull;

public class NamespacedKey {
	
	public static final Pattern NAMESPACE_PATTERN = Pattern.compile("^[a-z0-9\\.\\-_]+$");
	public static final Pattern KEY_PATTERN = Pattern.compile("^[a-z0-9\\.\\-_\\/]+$");
	public static final Pattern NAMESPACED_KEY_PATTERN = Pattern.compile("^[a-z0-9\\.\\-_]+:[a-z0-9\\.\\-_\\/]+$");
	
	public static final String MINECRAFT = "minecraft";
	public static final String ATLAS = "atlas";
	
	private final String key, namespace, combination;
	private int hash;
	
	private NamespacedKey(String namespace, String key, String namespacedKey) {
		this.key = key;
		this.namespace = namespace;
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
		 * Returns the name
		 * @return name
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
		 */
		@NotNull
		public NamespacedKey getNamespacedKey();
		
	}

	@Override
	public int hashCode() {
		if (hash != 0)
			return hash;
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
		return hash = result;
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
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (namespace == null) {
			if (other.namespace != null)
				return false;
		} else if (!namespace.equals(other.namespace))
			return false;
		return true;
	}

	public static NamespacedKey of(String namespacedKey) {
		if (namespacedKey == null)
			throw new IllegalArgumentException("NamespacedKey can not be null!");
		int first = namespacedKey.indexOf(':');
		if (first < 1 || !NAMESPACED_KEY_PATTERN.matcher(namespacedKey).matches())
			throw new IllegalArgumentException("Invalid namespaced key: " + namespacedKey);
		String namespace = namespacedKey.substring(0, first);
		String key = namespacedKey.substring(first+1);
		return new NamespacedKey(namespace, key, namespacedKey);
	}
	
	public static NamespacedKey of(String namespace, String key) {
		if (namespace == null) 
			throw new IllegalArgumentException("Namespace can not be null!");
		if (key == null) 
			throw new IllegalArgumentException("Name can not be null!");
		String namespacedKey = namespace + ":" + key;
		if (!NAMESPACED_KEY_PATTERN.matcher(namespacedKey).matches())
			throw new IllegalArgumentException("Invalid namespaced key: " + namespacedKey);
		return new NamespacedKey(namespace, key, namespacedKey);
	}

}
