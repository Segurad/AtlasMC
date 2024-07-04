package de.atlasmc.util.map;

public class IntSet {
	
	private final Object OBJECT = new Object();
	
	private final Int2ObjectMap<Object> map;
	
	public IntSet() {
		map = new Int2ObjectMap<>();
	}
	
	public boolean contains(int value) {
		return map.containsKey(value);
	}
	
	public boolean remove(int value) {
		return map.remove(value) != null;
	}
	
	public boolean add(int value) {
		return map.put(value, OBJECT) == null;
	}
	
	public int size() {
		return map.size();
	}

}
