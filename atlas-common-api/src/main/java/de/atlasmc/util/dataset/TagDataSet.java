package de.atlasmc.util.dataset;

import java.util.Iterator;
import java.util.Objects;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.tag.Tag;

public final class TagDataSet<T extends Namespaced> extends AbstractDataSet<T> {
	
	private final Tag<T> tag;
	
	public TagDataSet(Tag<T> tag) {
		this.tag = Objects.requireNonNull(tag, "tag");
	}
	
	public Tag<T> getTag() {
		return tag;
	}

	@Override
	public boolean contains(Object value) {
		return tag.isTaged(value);
	}
	
	@Override
	public int size() {
		return tag.size();
	}

	@Override
	public int hashCode() {
		return tag.hashCode();
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

	@Override
	public Iterator<T> iterator() {
		return tag.iterator();
	}

}
