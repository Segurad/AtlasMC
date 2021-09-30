package de.atlasmc;

import java.util.ArrayList;
import java.util.List;

public class NamespacedKey {
	
	private static final List<String> spaces;
	private static short nextID = 0;
	private final String key, namespace;
	private final short namespaceID;
	
	static {
		spaces = new ArrayList<>();
		spaces.add(nextID++, "minecraft");
	}
	
	/**
	 * 
	 * @param namespace
	 * @return the namespaceID
	 */
	public static short registerNamespace(String namespace) {
		if (nextID < 0) throw new Error("Can not register more Namespaces!");
		if (namespace == null) throw new IllegalArgumentException("Namespace can not be null!");
		if (spaces.contains(namespace)) throw new IllegalArgumentException("Namespace already registered!");
		spaces.add(nextID, namespace);
		return nextID++;
	}
	
	/**
	 * 
	 * @param namespace
	 * @return the namespaceID or -1
	 */
	public static short getNamespaceID(String namespace) {
		if (namespace == null) throw new IllegalArgumentException("Namespace can not be null!");
		return (short) spaces.indexOf(namespace);
	}
	
	/**
	 * 
	 * @param namespaceID
	 * @return the namespace or null
	 */
	public static String getNamespace(int namespaceID) {
		if (namespaceID < 0 || namespaceID > nextID) throw new IllegalArgumentException("NamespaceID is not between 0 and " + (nextID-1) +": " + namespaceID);
		return spaces.get(namespaceID);
	}
	
	public NamespacedKey(String namespace, String key) {
		if (namespace == null) throw new IllegalArgumentException("Namespace can not be null!");
		if (key == null) throw new IllegalArgumentException("Key can not be null!");
		if (spaces.contains(namespace)) throw new IllegalArgumentException("Unknown Namespace!");
		this.key = key;
		this.namespace = namespace;
		this.namespaceID = (short) spaces.indexOf(key);
	}
	
	public NamespacedKey(int namespaceID, String key) {
		this.namespace = getNamespace(namespaceID);
		if (key == null) throw new IllegalArgumentException("Key can not be null!");
		if (namespace == null) throw new IllegalArgumentException("Unknown NamespaceID!");
		this.namespaceID = (short) namespaceID;
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	public short getNamespaceID() {
		return namespaceID;
	}
	
	public String getNamespace() {
		return namespace;
	}
	
	public static List<String> getNamespaces() {
		return new ArrayList<>(); 
	}
	
	public static interface Namespaced {
		
		public short getNamespaceID();
		
		public String getName();
		
		public default String getNamespacedName() {
			return NamespacedKey.getNamespace(getNamespaceID())+':'+getName();
		}
		
		public default NamespacedKey getNamespacedKey() {
			return new NamespacedKey(getNamespaceID(), getName());
		}
		
	}

}
