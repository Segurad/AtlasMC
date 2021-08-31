package de.atlasmc.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class is like a LinkedQueue without the poll feature and a special iterator which hops from node to node
 */
public class ConcurrentLinkedCollection<E> implements Iterable<E>, Collection<E> {
	
	private volatile Node<E> head, tail;
	private volatile int count = 0;
	
	static final class Node<T> {
		
		volatile boolean removed;
		volatile Node<T> prev, next;
		final T element;
		
		public Node(T element, Node<T> prev, Node<T> next) {
			this.element = element;
			this.prev = prev;
			this.next = next;
		}
		
	}
	
	@Override
	public boolean add(E entry) {
		count++;
		if (head == null) {
			head = new Node<E>(entry, null, null);
			tail = head;
			return true;
		}
		tail.next = new Node<E>(entry, tail, null);
		tail = tail.next;
		return true;
	}
	
	public void addFirst(E entry) {
		count++;
		if (head == null) {
			head = new Node<E>(entry, null, null);
			tail = head;
			return;
		}
		head.prev = new Node<E>(entry, null, head);
		head = head.prev;
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
		count = 0;
	}
	
	@Override
	public boolean contains(Object entry) {
		Node<E> next = head;
		while(next != null) {
			if (entry.equals(next.element)) return true;
			next = next.next;
		}
		return false;
	}
	
	@Override
	public boolean remove(Object entry) {
		Node<E> next = head;
		while(next != null) {
			if (!entry.equals(next.element)) {
				next = nextValid(next);
				continue;
			}
			removeNode(next);
			return true;
		}
		return false;
	}
	
	private void removeNode(Node<E> node) {
		if (node.removed) return;
		node.removed = true;
		count--;
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
	
	private void insertAfter(Node<E> node, E entry) {
		if (node.removed) node = prevValid(node);
		if (node == null) {
			addFirst(entry);
			return;
		}
		Node<E> next = nextValid(node);
		Node<E> newNode = new Node<E>(entry, node, next);
		node.next = newNode;
		next.prev = newNode;
		count++;
	}
	
	private void insertBefor(Node<E> node, E entry) {
		if (node.removed) node = nextValid(node);
		if (node == null) {
			add(entry);
			return;
		}
		Node<E> prev = prevValid(node);
		Node<E> newNode = new Node<E>(entry, prev, node);
		node.prev = newNode;
		prev.next = newNode;
		count++;
	}

	@Override
	public LinkedCollectionIterator<E> iterator() {
		return new LinkedCollectionIterator<E>(this);
	}
	
	/**
	 * A Iterator that navigates between valid nodes<br>
	 * Repeatable behavior is not guaranteed due to changes made to the collection<br>
	 * @param <E>
	 */
	public static final class LinkedCollectionIterator<E> implements Iterator<E> {
		
		private Node<E> node, fetched;
		private final ConcurrentLinkedCollection<E> list;
		
		LinkedCollectionIterator(ConcurrentLinkedCollection<E> list) {
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
			return node == null ? null : node.element;
		}
		
		/**
		 * Returns the next element but does not move the iterators position
		 * @return the next element or null
		 */
		public E fetchNext() {
			fetched = list.nextValid(node);
			return fetched == null ? null : fetched.element;
		}
		
		/**
		 * Returns the previous element but does not move the iterators position
		 * @return the previous element or null
		 */
		public E fetchPrevious() {
			fetched = list.prevValid(node);
			return fetched == null ? null : fetched.element;
		}
		
		/**
		 * Moves to the last fetched element if available
		 */
		public void moveToFetched() {
			if (fetched == null) return;
			node = fetched;
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
			return node == null ? null : node.element;
		}

		@Override
		public void remove() {
			list.removeNode(node);
		}

		public void add(E e) {
			list.insertAfter(node, e);
		}
		
		public void addBefor(E e) {
			list.insertBefor(node, e);
		}
		
	}

	@Override
	public boolean isEmpty() {
		return count == 0;
	}

	@Override
	public Object[] toArray() {
		Object[] data = new Object[count];
		if (data.length == 0) return data;
		int index = 0;
		for (E e : this) {
			data[index] = e;
			index++;
			if (index >= data.length) break;
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
			if (index >= a.length) break;
		}
		if (index < a.length) Arrays.fill(a, index, a.length, null);
		return a;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o)) return false;
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E e : c) {
			add(e);
		}
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean changes = false;
		for (Object o : c) {
			if (remove(o)) changes = true;
		}
		return changes;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean changes = false;
		LinkedCollectionIterator<E> it = iterator();
		while (it.hasNext()) {
			if (c.contains(it.next())) continue;
			changes = true;
			it.remove();
		}
		return changes;
	}

}
