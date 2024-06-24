package de.atlasmc.cache;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.util.ConcurrentLinkedList;
import de.atlasmc.util.ConcurrentLinkedList.LinkedListIterator;

public class MapCache<K, V> extends AbstractMap<K, V> implements CacheHolder {
	
	private final Map<K, CacheEntry<K, V>> map;
	private final ConcurrentLinkedList<CacheEntry<K, V>> ttlList;
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
	}
	
	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean containsKey(Object key) {
		CacheEntry<K, V> entry = map.get(key);
		return entry.newttl > currentTick;
	}

	@Override
	public boolean containsValue(Object value) {
		for (CacheEntry<K, V> entry : map.values()) {
			V v = entry.value;
			if (v == value || (v != null && v.equals(value))) {
				if (entry.newttl < currentTick)
					continue;
				return true;
			}
		}
		return false;
	}

	@Override
	public V get(Object key) {
		CacheEntry<K, V> entry = map.get(key);
		int currentTick = this.currentTick;
		if (entry.newttl < currentTick)
			return null;
		entry.newttl += entry.ttlIncrement;
		return entry.value;
	}

	@Override
	public V put(K key, V value) {
		return put(key, value, defaultTTL);
	}

	public V put(K key, V value, int ttl) {
		int currentTick = this.currentTick;
		CacheEntry<K, V> newEntry = new CacheEntry<>(key, value, ttl, ttl + currentTick);
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
		return entry.value;
	}

	@Override
	public void clear() {
		map.clear();
		ttlList.clear();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		if (entrySet == null)
			entrySet = new EntrySet<>(this);
		return entrySet;
	}

	@Override
	public void cleanUp() {
		int currentTick = ++this.currentTick;
		LinkedListIterator<CacheEntry<K, V>> it = ttlList.iterator();
		CacheEntry<K, V> entry = null;
		while ((entry = it.next()) != null) {
			if (entry.ttl >= currentTick)
				break;
			it.remove();
			int newttl = entry.newttl;
			if (newttl < currentTick) {
				map.remove(entry.key, entry);
				continue;
			}
			entry.ttl += newttl;
			insertTTL(entry);
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
				if (nextValue.ttl < currentTTL)
					continue;
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
		public final int ttlIncrement;
		public volatile int ttl;
		public volatile int newttl;
		
		public CacheEntry(K key, V value, int ttlIncrement, int ttl) {
			this.key = key;
			this.value = value;
			this.ttlIncrement = ttlIncrement;
			this.ttl = ttl;
			this.newttl = ttl;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			V old = this.value;
			this.value = value;
			return old;
		}
		
	}

}
