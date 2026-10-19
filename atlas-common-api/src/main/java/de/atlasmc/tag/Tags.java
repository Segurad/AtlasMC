package de.atlasmc.tag;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.ProtocolRegistry;
import de.atlasmc.registry.ProtocolRegistryValue;
import de.atlasmc.registry.Registry;
import de.atlasmc.util.map.CopyOnWriteArrayListMultimap;
import de.atlasmc.util.map.Multimap;

public class Tags {
	
	private static final Multimap<NamespacedKey, Tag<?>> registryTags;
	private static final Map<NamespacedKey, Tag<?>> tags;
	private static final Collection<Tag<?>> tagsView;
	private static final Lock modifyLock = new ReentrantLock();
	
	static {
		registryTags = new CopyOnWriteArrayListMultimap<>();
		tags = new ConcurrentHashMap<>();
		tagsView = Collections.unmodifiableCollection(tags.values());
	}
	
	public static <T> Tag<T> createTag(NamespacedKey key, Class<?> clazz) {
		return createTag(key, clazz, null);
	}
	
	public static <T extends ProtocolRegistryValue> ProtocolTag<T> createTag(NamespacedKey key, ProtocolRegistry<T> registry) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(registry, "registry");
		try {
			modifyLock.lock();
			Tag<?> rawTag = tags.get(key);
			if (rawTag != null) {
				if (!(rawTag instanceof ProtocolTag)) {
					throw new IllegalStateException("Tag present but was not ProtocolTag: " + key.toString());
				}
				@SuppressWarnings("unchecked")
				ProtocolTag<T> tag = (ProtocolTag<T>) rawTag;
				return tag;
			}
			ProtocolTag<T> tag = new ProtocolTag<T>(key, registry);
			tags.put(key, tag);
			registryTags.put(registry.getNamespacedKey(), tag);
			return tag;
		} finally {
			modifyLock.unlock();
		}
	}
	
	public static <T> Tag<T> createTag(NamespacedKey key, Registry<T> registry) {
		if (registry == null)
			throw new IllegalArgumentException("Registry can not be null!");
		return createTag(key, registry.getType(), registry);
	}
	
	private static <T> Tag<T> createTag(NamespacedKey key, Class<?> clazz, Registry<T> registry) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(clazz, "clazz");
		try {
			modifyLock.lock();
			Tag<?> rawTag = tags.get(key);
			if (rawTag != null) {
				@SuppressWarnings("unchecked")
				Tag<T> tag = (Tag<T>) rawTag;
				return tag;
			}
			Tag<T> tag = new Tag<T>(key, clazz);
			tags.put(key, tag);
			if (registry != null)
				registryTags.put(registry.getNamespacedKey(), tag);
			return tag;
		} finally {
			modifyLock.unlock();
		}
	}
	
	public static <T extends Tag<?>> T getTag(NamespacedKey key) {
		@SuppressWarnings("unchecked")
		T tag = (T) tags.get(key);
		return tag;
	}
	
	public static Collection<Tag<?>> getTags() {
		return tagsView;
	}
	
	public static boolean isTagged(NamespacedKey tagKey, Object value) {
		Tag<?> tag = getTag(tagKey);
		if (tag == null)
			return false;
		return tag.isTaged(value);
	}

}
