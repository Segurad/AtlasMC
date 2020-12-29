package de.atlasmc;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.util.Validate;

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
		Validate.notNull(namespace, "Namespace can not be null!");
		Validate.isFalse(spaces.contains(namespace), "Namespace already registered!");
		if (nextID < 0) throw new Error("Can not register more Namespaces");
		spaces.add(nextID, namespace);
		return nextID++;
	}
	
	/**
	 * 
	 * @param namespace
	 * @return the namespaceID or -1
	 */
	public static short getNamespaceID(String namespace) {
		Validate.notNull(namespace, "Namespace can not be null!");
		return (short) spaces.indexOf(namespace);
	}
	
	/**
	 * 
	 * @param namespaceID
	 * @return the namespace or null
	 */
	public static String getNamespace(int namespaceID) {
		Validate.isTrue(namespaceID >= 0 && namespaceID < nextID, "NamespaceID is not between 0 and " + (nextID-1) +": " + namespaceID);
		return spaces.get(namespaceID);
	}
	
	public NamespacedKey(String namespace, String key) {
		Validate.notNull(namespace, "Namespace can not be null!");
		Validate.notNull(key, "Key can not be null!");
		Validate.isTrue(namespace.equals("minecraft") || spaces.contains(namespace), "Unknown Namespace!");
		this.key = key;
		this.namespace = namespace;
		this.namespaceID = (short) spaces.indexOf(key);
	}
	
	public NamespacedKey(int namespaceID, String key) {
		this.namespace = getNamespace(namespaceID);
		Validate.notNull(key, "Key can not be null!");
		Validate.isTrue(namespace == null, "Unknown NamespaceID!");
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

}
