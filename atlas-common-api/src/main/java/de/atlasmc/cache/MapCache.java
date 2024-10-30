package de.atlasmc.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.util.ConcurrentLinkedList;
import de.atlasmc.util.ConcurrentLinkedList.LinkedListIterator;
import de.atlasmc.util.reference.WeakReference1;

/**
 * A {@link CacheHolder} that maps keys to values.
 * This implementation will keep a reference to a value until a time to live is exceeded.
 * After this a {@link WeakReference} will held to regain a object access until no references are left.
 * This holder will not be automatically registered to a {@link CacheHandler}.
 * @param <K>
 * @param <V>
 */
public class MapCache<K, V> extends AbstractMap<K, V> implements CacheHolder {
	
	private final Map<K, CacheEntry<K, V>> map;
	private final ConcurrentLinkedList<CacheEntry<K, V>> ttlList;
	private final ReferenceQueue<V> refQueue;
	private EntrySet<K, V> entrySet;
	private final int defaultTTL;
	private volatile int currentTick;
	
	public MapCache() {
		this(6000);
	}
	
	public MapCache(int defaultTimeToLive) {
		this.defaultTTL = defaultTimeToLive;
		this.map = new ConcurrentHashMap<>();
		this.ttlList = new ConcurrentLinkedList<>();
		this.refQueue = new ReferenceQueue<>();
	}
	
	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean containsKey(Object key) {
		return getNoTouch(key) != null;
	}

	@Override
	public boolean containsValue(Object value) {
		@SuppressWarnings("unchecked")
		V entryValue = (V) value; // TODO test if try catch ClassCastException can should be used
		for (CacheEntry<K, V> entry : map.values()) {
			if (entry.ref.refersTo(entryValue))
				return true;
		}
		return false;
	}

	/**
	 * Returns the entry mapped to the given key or null if not present.
	 * If the entry is present in the cache the TTL will be updated.
	 */
	@Override
	public V get(Object key) {
		final CacheEntry<K, V> entry = map.get(key);
		final int currentTick = this.currentTick;
		V value = entry.value;
		if (value == null || entry.newttl < currentTick) {
			value = entry.ref.get();
			if (value != null) { // resurrect entry
				entry.newttl = currentTick + entry.ttlIncrement;
				entry.value = value;
				insertTTL(entry);
			}
			return value;
		}
		entry.newttl = currentTick + entry.ttlIncrement;
		return entry.value;
	}
	
	/**
	 * Returns the value mapped to the given key or null if not present.
	 * Will not touch the entry and therefore will not update the TTL of the entry.
	 * @param key
	 * @return value or null
	 */
	public V getNoTouch(Object key) {
		CacheEntry<K, V> entry = map.get(key);
		if (entry == null)
			return null;
		V value = entry.ref.get();
		return value;
	}

	@Override
	public V put(K key, V value) {
		return put(key, value, defaultTTL);
	}

	public V put(K key, V value, int ttl) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (value == null)
			throw new IllegalArgumentException("Value can not be null!");
		int currentTick = this.currentTick;
		CacheEntry<K, V> newEntry = new CacheEntry<>(key, value, ttl, ttl + currentTick, refQueue);
		CacheEntry<K, V> oldEntry = map.put(key, newEntry);
		ttlList.remove(oldEntry);
		insertTTL(newEntry);
		if (oldEntry.newttl < currentTick)
			return null;
		return oldEntry.value;
	}

	@Override
	public V remove(Object key) {
		CacheEntry<K, V> entry = map.remove(key);
		if (entry.newttl < currentTick)
			return null;
		return entry.ref.get();
	}

	@Override
	public void clear() {
		map.clear();
		ttlList.clear();
	}

	/**
	 * Returns a entry set for all keys and values of this map. 
	 */
	@Override
	public Set<Entry<K, V>> entrySet() {
		if (entrySet == null)
			entrySet = new EntrySet<>(this);
		return entrySet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void cleanUp() {
		int currentTick = ++this.currentTick;
		LinkedListIterator<CacheEntry<K, V>> it = ttlList.iterator();
		CacheEntry<K, V> entry = null;
		while ((entry = it.next()) != null) {
			if (entry.ttl >= currentTick)
				continue;
			entry.value = null;
			if (!entry.ref.refersTo(null))
				continue;
			it.remove();
			int newttl = entry.newttl;
			if (newttl < currentTick) {
				map.remove(entry.key, entry);
				continue;
			}
			entry.ttl = newttl;
			insertTTL(entry);
		}
		WeakReference1<?, CacheEntry<K, V>> ref = null;
		while ((ref = (WeakReference1<?, CacheEntry<K, V>>) refQueue.poll()) != null) {
			CacheEntry<K, V> e = ref.value1;	
			map.remove(e.key, e);
		}
	}
	
	private void insertTTL(CacheEntry<K, V> insert) {
		LinkedListIterator<CacheEntry<K, V>> it = ttlList.iterator();
		CacheEntry<K, V> entry = null;
		int ttl = insert.ttl;
		while ((entry = it.next()) != null) {
			if (entry.newttl <= ttl) {
				it.addBefor(insert);
				return;
			}
		}
		ttlList.add(entry);
	}

	private static class EntrySet<K, V> extends AbstractSet<Entry<K, V>> {

		private final MapCache<K, V> cache;
		
		public EntrySet(MapCache<K, V> cache) {
			this.cache = cache;
		}
		
		@Override
		public int size() {
			return cache.size();
		}

		@Override
		public Iterator<Entry<K, V>> iterator() {
			return new EntryIterator<>(cache);
		}
		
	}
	
	private static class EntryIterator<K, V> implements Iterator<Entry<K, V>> {
		
		private final MapCache<K, V> cache;
		private final Iterator<?> it;
		private CacheEntry<K, V> last;
		private CacheEntry<K, V> next;
		
		public EntryIterator(MapCache<K, V> cache) {
			this.cache = cache;
			it = cache.entrySet().iterator();
		}

		@Override
		public boolean hasNext() {
			if (next != null)
				return true;
			CacheEntry<K, V> next = internalNext();
			if (next == null)
				return false;
			this.next = next;
			return true;
		}

		@Override
		public Entry<K, V> next() {
			CacheEntry<K, V> next = this.next;
			if (next != null) {
				this.next = null;
			} else {
				next = internalNext();
			}
			this.last = next;
			return next;
		}
		
		private CacheEntry<K, V> internalNext() {
			final int currentTTL = cache.currentTick;
			while (it.hasNext()) {
				@SuppressWarnings("unchecked")
				Entry<K, CacheEntry<K, V>> next = (Entry<K, CacheEntry<K, V>>) it.next();
				CacheEntry<K, V> nextValue = next.getValue();
				if (nextValue.ttl >= currentTTL) // TTL is valid
					return nextValue;
				if (!nextValue.ref.refersTo(null)) // TTL ended but held alive by reference
					return nextValue;
			}
			return null;
		}
		
		@Override
		public void remove() {
			if (last == null)
				return;
			cache.map.remove(last.key, last);
			cache.ttlList.remove(last);
			last = null;
		}
		
	}
	
	private static class CacheEntry<K, V> implements Entry<K, V> {
		
		public final K key;
		public volatile V value;
		public final WeakReference1<V, CacheEntry<K, V>> ref;
		public final int ttlIncrement;
		public volatile int ttl;
		public volatile int newttl;
		
		public CacheEntry(K key, V value, int ttlIncrement, int ttl, ReferenceQueue<V> queue) {
			this.key = key;
			this.value = value;
			this.ttlIncrement = ttlIncrement;
			this.ttl = ttl;
			this.newttl = ttl;
			this.ref = new WeakReference1<>(value, queue, this);
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return ref.get();
		}

		@Override
		public V setValue(V value) {
			V old = this.value;
			this.value = value;
			return old;
		}
		
	}

}
