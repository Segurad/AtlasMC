package de.atlasmc.util.dataset;

import java.util.Collection;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.tag.Tag;

public class TagDataSet<T extends Namespaced> implements DataSet<T> {
	
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

}
