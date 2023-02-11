package de.atlasmc;

import de.atlasmc.util.annotation.NotNull;

public class NamespacedKey {
	
	public static final String MINECRAFT = "minecraft";
	
	private final String name, namespace, combination;
	
	public NamespacedKey(String namespacedKey) {
		if (namespacedKey == null)
			throw new IllegalArgumentException("NamespacedKey can not be null!");
		int first = namespacedKey.indexOf(':');
		if (first < 1)
			throw new IllegalArgumentException("Illegal NamespacedKey: " + namespacedKey);
		namespace = namespacedKey.substring(0, first);
		name = namespacedKey.substring(first+1);
		this.combination = namespacedKey;
	}
	
	public NamespacedKey(String namespace, String name) {
		if (namespace == null) 
			throw new IllegalArgumentException("Namespace can not be null!");
		if (name == null) 
			throw new IllegalArgumentException("Name can not be null!");
		this.name = name;
		this.namespace = namespace;
		this.combination = namespace + ":" + name;
	}
	
	public String getName() {
		return name;
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
		public default String getName() {
			return getNamespacedKey().getName();
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
		 * @return namespacedname
		 */
		@NotNull
		public default String getNamespacedName() {
			return getNamespacedKey().toString();
		}
		
		/**
		 * Returns the {@link NamespacedKey} of this Object
		 */
		@NotNull
		public NamespacedKey getNamespacedKey();
		
	}

}
