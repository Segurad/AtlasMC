package de.atlasmc.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.ThreadSafe;

/**
 * This class is a thread safe implementation of a LinkedList using volatile for the most reading operations and synchronized for manipulation
 */
@ThreadSafe
public class ConcurrentLinkedList<E> implements Iterable<E>, Collection<E> {
	
	private volatile Node<E> head, tail;
	private volatile int count = 0; // Modify only by sync over ConcurrentLikedList.this
	
	@Override
	public boolean add(E entry) {
		internalAdd(entry);
		return true;
	}
	
	public EntryPointer<E> addWithPointer(E entry) {
		return new EntryPointer<E>(this, internalAdd(entry));
	}
	
	public EntryPointer<E> addFirstWithPointer(E entry) {
		return new EntryPointer<E>(this, internalAddFirst(entry));
	}
	
	public void addFirst(E entry) {
		internalAddFirst(entry);
	}
	
	private synchronized Node<E> internalAddFirst(E entry) {
		if (entry == null)
			throw new IllegalArgumentException("Entry can not be null!");
		incrementCount();
		if (head == null) {
			head = new Node<E>(entry, null, null);
			tail = head;
			return head;
		}
		head.prev = new Node<E>(entry, null, head);
		head = head.prev;
		return head;
	}
	
	public int size() {
		return count;
	}
	
	public synchronized void clear() {
		if (head == null) return;
		Node<E> node = head;
		head = null;
		tail = null;
		while(node != null) {
			node.removed = true;
			node = node.next;
		}
		count = 0; // no modify method needed is in sync method
	}
	
	@Override
	public boolean contains(Object entry) {
		if (entry == null)
			return false;
		Node<E> next = head;
		while(next != null) {
			if (entry.equals(next.entry)) return true;
			next = next.next;
		}
		return false;
	}
	
	@Override
	public boolean remove(Object entry) {
		if (entry == null)
			return false;
		Node<E> next = head;
		while(next != null) {
			if (!entry.equals(next.entry)) {
				next = nextValid(next);
				continue;
			}
			removeNode(next);
			return true;
		}
		return false;
	}
	
	private synchronized void removeNode(Node<E> node) {
		if (node.removed) 
			return;
		node.removed = true;
		decrementCount();
		Node<E> prev = prevValid(node), next = nextValid(node);
		if (prev != null) prev.next = next;
		if (next != null) next.prev = prev;
		if (node == head) updateHead(next);
		if (node == tail) updateTail(prev);
	}
	
	private Node<E> nextValid(Node<E> node) {
		node = node.next;
		while (node != null) {
			if (!node.removed) return node;
			node = node.next;
		}
		return null;
	}
	
	private Node<E> prevValid(Node<E> node) {
		node = node.prev;
		while (node != null) {
			if (!node.removed) return node;
			node = node.prev;
		}
		return null;
	}
	
	private void updateHead(Node<E> node) {
		head = node;
	}
	
	private void updateTail(Node<E> node) {
		tail = node;
	}
	
	private synchronized Node<E> internalAdd(E entry) {
		if (entry == null)
			throw new IllegalArgumentException("Entry can not be null!");
		if (head == null) {
			head = new Node<E>(entry, null, null);
			tail = head;
			return tail;
		}
		tail.next = new Node<E>(entry, tail, null);
		tail = tail.next;
		incrementCount();
		return tail;
	}
	
	private synchronized void insertAfter(Node<E> node, E entry) {
		if (entry == null)
			throw new IllegalArgumentException("Entry can not be null!");
		if (node.removed) 
			node = prevValid(node);
		if (node == null) {
			addFirst(entry);
			return;
		}
		Node<E> next = nextValid(node);
		Node<E> newNode = new Node<E>(entry, node, next);
		node.next = newNode;
		next.prev = newNode;
		incrementCount();
	}
	
	private synchronized void insertBefor(Node<E> node, E entry) {
		if (node.removed) 
			node = nextValid(node);
		if (node == null) {
			add(entry);
			return;
		}
		Node<E> prev = prevValid(node);
		Node<E> newNode = new Node<E>(entry, prev, node);
		node.prev = newNode;
		prev.next = newNode;
		incrementCount();
	}
	
	private synchronized void incrementCount() {
		count++;
	}
	
	private synchronized void decrementCount() {
		count--;
	}

	@Override
	public LinkedListIterator<E> iterator() {
		return new LinkedListIterator<E>(this);
	}

	@Override
	public boolean isEmpty() {
		return count == 0;
	}

	@Override
	public Object[] toArray() {
		Object[] data = new Object[count];
		if (data.length == 0) 
			return data;
		int index = 0;
		for (E e : this) {
			data[index] = e;
			index++;
			if (index >= data.length) 
				break;
		}
		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		if (a.length < count) {
			a = Arrays.copyOf(a, count);
		}
		int index = 0;
		for (E e : this) {
			a[index] = (T) e;
			index++;
			if (index >= a.length) 
				break;
		}
		if (index < a.length) 
			Arrays.fill(a, index, a.length, null);
		return a;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o)) 
				return false;
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (c == null)
			return false;
		boolean changes = false;
		for (E e : c) {
			if (add(e))
				changes = true;
		}
		return changes;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if (c == null)
			return false;
		if (c.isEmpty())
			return false;
		boolean changes = false;
		for (Object o : c) {
			if (remove(o)) 
				changes = true;
		}
		return changes;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		if (c == null)
			return false;
		boolean changes = false;
		LinkedListIterator<E> it = iterator();
		while (it.hasNext()) {
			if (c.contains(it.next())) 
				continue;
			changes = true;
			it.remove();
		}
		return changes;
	}
	
	static final class Node<T> {
		
		volatile boolean removed;
		volatile Node<T> prev, next;
		volatile T entry;
		
		public Node(T entry, Node<T> prev, Node<T> next) {
			this.entry = entry;
			this.prev = prev;
			this.next = next;
		}
		
	}
	
	/**
	 * A Iterator that navigates between valid nodes<br>
	 * Repeatable behavior is not guaranteed due to asynchronous changes made to the list<br>
	 * @param <E>
	 */
	public static final class LinkedListIterator<E> implements Iterator<E> {
		
		private Node<E> node, peeked;
		private final ConcurrentLinkedList<E> list;
		
		LinkedListIterator(ConcurrentLinkedList<E> list) {
			this.list = list;
			node = new Node<E>(null, null, list.head);
		}
		
		@Override
		public boolean hasNext() {
			return list.nextValid(node) != null;
		}

		@Override
		public E next() {
			node = list.nextValid(node);
			return node == null ? null : node.entry;
		}
		
		/**
		 * Returns the element of the current node
		 * @return element or null
		 */
		@Nullable
		public E get() {
			if (node == null)
				return null;
			return node.entry;
		}
		
		/**
		 * Returns a {@link EntryPointer} of the current node
		 * @return pointer
		 */
		public EntryPointer<E> getPointer() {
			if (node == null)
				return null;
			return new EntryPointer<>(list, node);
		}
		
		/**
		 * Returns the next element but does not move the iterators position
		 * @return the next element or null
		 */
		public E peekNext() {
			peeked = list.nextValid(node);
			return peeked == null ? null : peeked.entry;
		}
		
		/**
		 * Returns the previous element but does not move the iterators position
		 * @return the previous element or null
		 */
		public E peekPrevious() {
			peeked = list.prevValid(node);
			return peeked == null ? null : peeked.entry;
		}
		
		/**
		 * Goto the last peeked element if available
		 */
		public void gotoPeeked() {
			if (peeked == null) return;
			node = peeked;
		}
		
		/**
		 * Goto the first element of this list and returns it
		 * @return element
		 */
		public E gotoHead() {
			node = list.head;
			return node.entry;
		}
		
		/**
		 * Goto the last element of this list and returns it
		 * @return element
		 */
		public E gotoTail() {
			node = list.tail;
			return node.entry;
		}

		public boolean hasPrevious() {
			return list.prevValid(node) != null;
		}

		/**
		 * May or may not return the previous of the current element due to changes made to the collection<br>
		 * e.g. does not return the element you had before if it is no longer in this collection or a new element had been inserted
		 * @return the current previous or null
		 */
		public E previous() {
			node = list.prevValid(node);
			return node == null ? null : node.entry;
		}

		@Override
		public void remove() {
			list.removeNode(node);
		}

		public void add(E entry) {
			if (entry == null)
				throw new IllegalArgumentException("Entry can not be null!");
			list.insertAfter(node, entry);
		}
		
		public void addBefor(E entry) {
			if (entry == null)
				throw new IllegalArgumentException("Entry can not be null!");
			list.insertBefor(node, entry);
		}
		
	}
	
	/**
	 * Points to a Node and allows to remove or change it
	 * @param <E>
	 */
	public static final class EntryPointer<E> {
		
		private final Node<E> node;
		private final ConcurrentLinkedList<E> list;
		
		private EntryPointer(ConcurrentLinkedList<E> list, Node<E> node) {
			this.list = list;
			this.node = node;
		}
		
		/**
		 * Gets the entry of the node
		 * @return entry
		 */
		@NotNull
		public E get() {
			return node.entry;
		}
		
		/**
		 * Sets the entry of the node
		 * @param entry
		 */
		public void set(@NotNull E entry) {
			if (entry == null)
				throw new IllegalArgumentException("Entry can not be null!");
			node.entry = entry;
		}
		
		public void remove() {
			list.removeNode(node);
		}
		
		public boolean isRemoved() {
			return node.removed;
		}
		
	}

}
