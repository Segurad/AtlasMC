package de.atlasmc.util.pipeline;

public interface Pipeline<E> {
	
	E get(String name);
	
	boolean contains(E entry);
	
	boolean contains(String name);
	
	boolean addFirst(String name, E entry);
	
	boolean addLast(String name, E entry);
	
	boolean addBefore(String before, String name, E entry);
	
	boolean addAfter(String after, String name, E entry);
	
	boolean replace(String old, String name, E entry);
	
	boolean remove(E entry);
	
	boolean remove(String name);
	
	boolean clear();

	default boolean isEmpty() {
		return size() == 0;
	}
	
	int size();

}
