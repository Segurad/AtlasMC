package de.atlasmc.util.dataset;

import java.util.Collection;
import java.util.Objects;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.tag.Tag;

public class TagDataSet<T extends Namespaced> extends AbstractDataSet<T> {
	
	private final Tag<T> tag;
	
	public TagDataSet(Tag<T> tag) {
		if (tag == null)
			throw new IllegalArgumentException("Tag can not be null!");
		this.tag = tag;
	}
	
	public Tag<T> getTag() {
		return tag;
	}

	@Override
	public Collection<T> values() {
		return tag.getValues();
	}

	@Override
	public boolean contains(T value) {
		return tag.isTaged(value);
	}
	
	@Override
	public int size() {
		return tag.size();
	}
	
	@Override
	public boolean isEmpty() {
		return tag.isEmpty();
	}

	@Override
	public int hashCode() {
		return Objects.hash(tag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return super.equals(obj);
		TagDataSet<?> other = (TagDataSet<?>) obj;
		return Objects.equals(tag, other.tag);
	}

}
