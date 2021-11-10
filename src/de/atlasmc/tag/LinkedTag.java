package de.atlasmc.tag;

import java.util.Set;

/**
 * Links {@link TagContainer} together
 */
public class LinkedTag<T> extends Tag<T> {
	
	private Set<Tag<T>> tagcontainer;

	public void addTag(Tag<T> tag) {
		tagcontainer.add(tag);
	}
	
	public void removeTag(Tag<T> tag) {
		tagcontainer.remove(tag);
	}
	
	@Override
	public boolean isTaged(T element) {
		for (Tag<T> t : tagcontainer) {
			if (t.isTaged(element)) return true;
		}
		return false;
	}

	@Override
	public Set<T> getValues() {
		Set<T> s = Set.of();
		for (Tag<T> t : tagcontainer) {
			s.addAll(t.getValues());
		}
		return s;
	}
	
	public Set<Tag<T>> getTags() {
		return tagcontainer;
	}

}
