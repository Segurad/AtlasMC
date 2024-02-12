package de.atlasmc.loot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class LootManager<T> {

	protected final Random random;
	protected final HashMap<T, LootTable> defaulttables;
	
	public LootManager() {
		random = new Random();
		defaulttables = new HashMap<>();
	}
	
	public LootTable getTable(T key) {
		return defaulttables.get(key);
	}
	
	public LootTable setTable(T key, LootTable table) {
		if (table == null) {
			defaulttables.remove(key);
			return null;
		}
		return defaulttables.put(key, table);
	}
	
	public boolean containsKey(T key) {
		return defaulttables.containsKey(key);
	}
	
	public boolean containsTable(LootTable table) {
		return defaulttables.containsValue(table);
	}
	
	public Set<T> getKeys() {
		return defaulttables.keySet();
	}
	
	public Collection<LootTable> getTables() {
		return defaulttables.values();
	}
	
	public void removeTable(T key) {
		defaulttables.remove(key);
	}
	
	public void clear() {
		defaulttables.clear();
	}
	
	public Random getRandom() {
		return random;
	}
}
