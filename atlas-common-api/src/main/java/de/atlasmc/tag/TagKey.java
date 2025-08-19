package de.atlasmc.tag;

import de.atlasmc.NamespacedAccessKey;
import de.atlasmc.NamespacedKey;

public class TagKey<T> extends NamespacedAccessKey<Tag<T>> {
	
	public TagKey(NamespacedKey key) {
		super(key);
	}

	@Override
	public Tag<T> get() {
		return Tags.getTag(key);
	}

}
