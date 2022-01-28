package de.atlasmc.util.map;

/**
 * Maps primitive longs to Objects
 * @param <V>
 */
public class Long2ObjectHashMap<V> {
	
	private static final int MIN_SIZE = 32;
	private static final float DEFAULT_REHASH_DOWN = 0.25f;
	private static final float DEFAULT_REHASH_UP = 0.90f;
	
	private Node<V>[] values;
	private Node<V> head;
	private int size, used;
	private final float rehashUp, rehashDown;
	
	public Long2ObjectHashMap() {
		this(MIN_SIZE);
	}
	
	public Long2ObjectHashMap(int mincapacity) {
		this(mincapacity, DEFAULT_REHASH_UP, DEFAULT_REHASH_DOWN);
	}
	
	@SuppressWarnings("unchecked")
	public Long2ObjectHashMap(int mincapacity, float rehashUp, float rehashDown) {
		if (mincapacity < 0)
			throw new IllegalArgumentException("Mincapacity can not be lower than 0: " + mincapacity);
		if (rehashUp > 1)
			throw new IllegalArgumentException("RehashUp can not be higher than 1: " + rehashUp);
		if (rehashDown < 0) 
			throw new IllegalArgumentException("RehashDown can not be lower than 0: " + rehashDown);
		if (rehashUp <= rehashDown)
			throw new IllegalArgumentException("RehashUp can not be lower than rehashDown: " + rehashUp + " <= "+ rehashDown);
		values = new Node[mincapacity];
		size = mincapacity;
		this.rehashUp = rehashUp;
		this.rehashDown = rehashDown;
	}
	
	/**
	 * Puts the value in this map with the given key.<br>
	 * Overrides the value if already present
	 * @param key
	 * @param value
	 */
	public void put(long key, V value) {
		getNode(hash(key), key, value, true, null);
		ensureSize();
	}

	/**
	 * Removes the key and value from this map and returns the removed value or null if non
	 * @param key
	 * @return value or null
	 */
	public V remove(long key) {
		Node<V> node = getNode(hash(key), key, null, false, null);
		if (node == null)
			return null;
		removeNode(node);
		ensureSize();
		return node.value;
	}

	/**
	 * Returns the value mapped to the key or null if not present
	 * @param key
	 * @return value or null
	 */
	@SuppressWarnings("unchecked")
	public V get(long key) {
		int index = hash(key);
		return index == -1 ? null : (V) values[index];
	}
	
	/**
	 * 
	 * @param hash
	 * @param key
	 * @param value
	 * @param create if create this method will always return a node and if the node exist the value will be replaced
	 * @param existing Node that already exist added to reduce overhead for rehashing
	 * @return node or null
	 */
	private Node<V> getNode(int hash, long key, V value, boolean create, Node<V> existing) {
		if (used == 0) { // if used == 0 do direct insert
			if (!create) // escape if not create
				return null;
			head = new Node<V>(hash, key, value);
			values[hash] = head;
			return head;
		}
		Node<V> node = values[hash];
		if (node == null) { // when hash not present insert and find previous
			if (!create) // escape if not create
				return null;
			node = head;
			while (node.next != null && node.hash < hash) {
				node = node.next;
			}
			return insertAfter(hash, key, value, node, existing);
		}
		if (node.key == key)
			return node;
		if (!create)
			return null;
		do { // find node or node with higher key than node
			if (node.key > key) // nodes are ordered by key size if key lower than insert before
				return insertBefore(hash, key, value, node, existing); // insert before
			if (node.next == null)
				return insertAfter(hash, key, value, node, existing); // insert after
			node = node.next;
			if (node.key == key) {
				if (!create)
					node.value = value;
				return node;
			}
		} while (node.hash == hash);
		return insertBefore(hash, key, value, node, existing); 
	}
	
	private Node<V> insertAfter(int hash, long key, V value, Node<V> prev, Node<V> existing) {
		Node<V> node = existing == null ? new Node<>(hash, key, value) : existing;
		node.next = prev.next;
		prev.next = node;
		if (node.next != null)
			node.next.prev = node;
		used++;
		return node;
	}
	
	private Node<V> insertBefore(int hash, long key, V value, Node<V> next, Node<V> existing) {
		Node<V> node = existing == null ? new Node<>(hash, key, value) : existing;
		node.next = next;
		if (next.prev != null) {
			next.prev.next = node;
			node.prev = next.prev;
		} else head = node;
		next.prev = node;
		values[hash] = node;
		used++;
		return node;
	}
	
	private void removeNode(Node<V> node) {
		if (node.next != null) { // connect previous to next if next present
			node.next.prev = node.prev;
			if (node.hash == node.next.hash && values[node.hash] == node) // insert next in values if node is first for hash
				values[node.hash] = node.next;
		} else if (values[node.hash] == node) // clear values at hash if no next with hash
			values[node.hash] = null;
		if (node.prev != null) // connect next to previous if previous present
			node.prev.next = node.next;
		else // if previous == null it can be assumed the node is the head
			head = node.next;
		used--;
	}
	
	/**
	 * Returns the number of elements currently in this map
	 * @return size
	 */
	public int size() {
		return used;
	}
	
	/**
	 * Returns the index by from this key
	 * @param key
	 * @return hash
	 */
	private int hash(long key) {
		int hash = Long.hashCode(key);
		hash %= size; // trim hash to map size
		return hash;
	}
	
	/**
	 * Rehashed the map with the currently set size
	 */
	@SuppressWarnings("unchecked")
	private void rehash() {
		values = new Node[size];
		Node<V> oldHead = head;
		head = null;
		used = 0;
		while(oldHead != null) {
			Node<V> node = oldHead;
			oldHead = node.next;
			node.prev = null;
			node.next = null;
			node.hash = hash(node.key);
			getNode(node.hash, node.key, null, true, node);
		}
	}
	
	/**
	 * Checks if the map needs to grow or shrink
	 */
	private void ensureSize() {
		float load = used/size;
		if (load >= rehashUp) { 
			size <<= 1;
			rehash();
		} else if (load <= rehashDown && size > MIN_SIZE) {
			size >>= 1;
			rehash();
		}
	}
	
	private class Node<E> {
		private int hash;
		private final long key;
		private E value;
		private Node<E> prev, next;
		
		public Node(int hash, long key, E value) {
			this.hash = hash;
			this.key = key;
			this.value = value;
		}	
	}
}
