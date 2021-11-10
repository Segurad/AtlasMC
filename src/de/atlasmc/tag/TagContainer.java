package de.atlasmc.tag;

import java.util.Set;

/**
 * Contains all elements for this tag
 */
public class TagContainer<T> extends Tag<T> {
	
	private Set<T> taged;
	
	public TagContainer(Set<T> taged) {
		this.taged = taged;
	}
	
	public TagContainer() {
		this.taged = Set.of();
	}
	
	public boolean isTaged(T element) {
		return taged.contains(element);
	}
	
	public Set<T> getValues() {
		return taged;
	}

}
