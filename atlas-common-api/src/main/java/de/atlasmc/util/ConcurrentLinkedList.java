package de.atlasmc.util;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.ThreadSafe;

/**
 * Thread-safe doubly linked collection with weakly consistent cursor iterators.
 *
 * Iterators operate on node positions rather than indexes and support
 * insertion relative to the current cursor position.
 *
 * Concurrent modifications may affect traversal order.
 */
@ThreadSafe
public class ConcurrentLinkedList<E> extends AbstractCollection<E> {
	
	private volatile Node<E> head;
	private volatile Node<E> tail;
	private volatile int count = 0; // Modify only by sync over ConcurrentLinkedList.this
	
	@Override
	public boolean add(E entry) {
		internalAdd(entry);
		return true;
	}
	
	public void addFirst(E entry) {
		internalAddFirst(entry);
	}
	
	private synchronized Node<E> internalAddFirst(E entry) {
		if (entry == null)
			throw new IllegalArgumentException("Entry can not be null!");
		incrementCount();
		if (head == null) {
			head = new Node<>(entry, null, null);
			tail = head;
			return head;
		}
		head.prev = new Node<>(entry, null, head);
		head = head.prev;
		return head;
	}
	
	public E getHead() {
		Node<E> head = this.head;
		return head != null ? head.entry : null;
	}
	
	public E getTail() {
		Node<E> tail = this.tail;
		return tail != null ? tail.entry : null;
	}
	
	@Override
	public int size() {
		return count;
	}
	
	@Override
	public synchronized void clear() {
		if (head == null) 
			return;
		Node<E> node = head;
		head = null;
		tail = null;
		while(node != null) {
			node.removed = true;
			var next = node.next;
			node.next = null;
			node.prev = null;
			node = next;
		}
		count = 0; // no modify method needed is in sync method
	}
	
	@Override
	public boolean contains(Object entry) {
		if (entry == null)
			return false;
		Node<E> next = head;
		while(next != null) {
			if (entry.equals(next.entry)) 
				return true;
			next = nextValid(next);
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
			return removeNode(next);
		}
		return false;
	}
	
	private synchronized boolean removeNode(Node<E> node) {
		if (node.removed) 
			return false;
		node.removed = true;
		Node<E> prev = prevValid(node);
		Node<E> next = nextValid(node);
		if (prev != null) 
			prev.next = next;
		if (next != null) 
			next.prev = prev;
		if (node == head) 
			updateHead(next);
		if (node == tail) 
			updateTail(prev);
		decrementCount();
		return true;
	}
	
	/**
	 * Returns the next valid node or null of none
	 * @param node
	 * @return node or null
	 */
	@Nullable
	private Node<E> nextValid(@Nullable Node<E> node) {
		if (node == null)
			return null;
		node = node.next;
		while (node != null) {
			if (!node.removed) 
				return node;
			node = node.next;
		}
		return null;
	}
	
	/**
	 * Returns the previous valid node or null of none
	 * @param node
	 * @return node or null
	 */
	@Nullable
	private Node<E> prevValid(@Nullable Node<E> node) {
		if (node == null)
			return null;
		node = node.prev;
		while (node != null) {
			if (!node.removed) 
				return node;
			node = node.prev;
		}
		return null;
	}
	
	/**
	 * Sets the head node to the given node
	 * @param node
	 */
	private void updateHead(Node<E> node) {
		head = node;
	}
	
	/**
	 * Sets the tail node to the given node
	 * @param node
	 */
	private void updateTail(Node<E> node) {
		tail = node;
	}
	
	private synchronized Node<E> internalAdd(E entry) {
		if (entry == null)
			throw new IllegalArgumentException("Entry can not be null!");
		if (head == null) {
			head = new Node<>(entry, null, null);
			tail = head;
			incrementCount();
			return tail;
		}
		tail.next = new Node<>(entry, tail, null);
		tail = tail.next;
		incrementCount();
		return tail;
	}
	
	private synchronized void insertAfter(Node<E> node, E entry) {
		if (entry == null)
			throw new IllegalArgumentException("Entry can not be null!");
		if (node == null) {
			addFirst(entry);
			return;
		}
		if (node.removed) {
			node = prevValid(node);
		}
		Node<E> next = nextValid(node);
		Node<E> newNode = new Node<>(entry, node, next);
		node.next = newNode;
		if (next != null) {
			next.prev = newNode;
		} else {
			updateTail(newNode);
		}
		incrementCount();
	}
	
	private synchronized void insertBefore(Node<E> node, E entry) {
		insertAfter(prevValid(node), entry);
	}
	
	private void incrementCount() {
		count++;
	}
	
	private void decrementCount() {
		count--;
	}

	@Override
	public LinkedListIterator<E> iterator() {
		return new LinkedListIterator<>(this);
	}
	
	static final class Node<T> {
		
		volatile boolean removed;
		volatile Node<T> prev;
		volatile Node<T> next;
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
		
		private static final Node<?> DUMMY = new Node<>(null, null, null);
		
		private Node<E> node;
		private Node<E> nextNode; // stores the next peeked node
		private Node<E> previousNode; // stores the previous peeked node
		private final ConcurrentLinkedList<E> list;
		private boolean removed;
		
		LinkedListIterator(ConcurrentLinkedList<E> list) {
			this.list = list;
			reset();
		}
		
		/**
		 * Resets this iterator as if it was not used
		 */
		@SuppressWarnings("unchecked")
		public void reset() {
			this.node = (Node<E>) DUMMY;
			this.nextNode = null;
			this.previousNode = null;
			this.removed = false;
		}
		
		@Override
		public boolean hasNext() {
			return peekNext() != null;
		}
		
		public boolean hasPrevious() {
			return peekPrevious() != null;
		}

		@NotNull
		@Override
		public E next() {
			var node = this.nextNode;
			if (node != null)
				return gotoNode(node);
			node = this.node;
			node = node == DUMMY ? list.head : list.nextValid(node);
			if (node == null)
				throw new NoSuchElementException();
			return gotoNode(node);
		}
		
		/**
		 * May or may not return the previous of the current element due to changes made to the collection<br>
		 * e.g. does not return the element you had before if it is no longer in this collection or a new element had been inserted
		 * @return the current previous
		 * @throws NoSuchElementException if there is no previous node
		 */
		@NotNull
		public E previous() {
			var node = this.previousNode;
			if (node != null)
				return gotoNode(node);
			node = this.node;
			node = node == DUMMY ? null : list.prevValid(node);
			if (node == null)
				throw new NoSuchElementException();
			return gotoNode(node);
		}
		
		/**
		 * Returns the element of the current node or null if non valid
		 * @return element or null
		 */
		@Nullable
		public E get() {
			if (node == null || node == DUMMY || node.removed)
		        return null;
		    return node.entry;
		}
		
		/**
		 * Sets the element of the last returned node
		 * @param entry
		 * @return the entry that was replaces or null if in no valid state
		 */
		public E set(@NotNull E entry) {
			Objects.requireNonNull(entry);
			var node = this.node;
			if (node == null || node == DUMMY)
				return null;
			var old = node.entry;
			node.entry = entry;
			return old;
		}
		
		/**
		 * Returns the next element but does not move the iterators position.
		 * Returns null if there is no valid node.
		 * @return the next element or null
		 */
		@Nullable
		public E peekNext() {
			var node = this.node;
			var nextNode = node == DUMMY ? list.head : list.nextValid(node);
			this.nextNode = nextNode;
			this.previousNode = null;
			return nextNode == null ? null : nextNode.entry;
		}
		
		/**
		 * Returns the previous element but does not move the iterators position
		 * Returns null if there is no valid previous node.
		 * @return the previous element or null
		 */
		@Nullable
		public E peekPrevious() {
			var node = this.node;
			if (node == DUMMY || node == null)
				return null;
			var previousNode = list.prevValid(node);
			this.previousNode = previousNode;
			this.nextNode = null;
			return previousNode == null ? null : previousNode.entry;
		}
		
		/**
		 * Goto the last peeked element if available
		 */
		public void gotoPeeked() {
			var peeked = nextNode;
			if (peeked != null) {
				gotoNode(peeked);
				return;
			}
			peeked = previousNode;
			if (peeked != null) {
				gotoNode(peeked);
			}
		}
		
		/**
		 * Goto the first element of this list and returns it null if the list is empty
		 * @return element or null
		 */
		@Nullable
		public E gotoHead() {
			return gotoNode(list.head);
		}
		
		/**
		 * Goto the last element of this list and returns it null if the list is empty
		 * @return element or null
		 */
		@Nullable
		public E gotoTail() {
			return gotoNode(list.tail);
		}
		
		private E gotoNode(Node<E> node) {
			this.node = node;
			nextNode = null;
			previousNode = null;
			removed = false;
			return node != null ? node.entry : null;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void remove() {
			if (removed)
				throw new IllegalStateException();
			if (node == DUMMY)
				return;
			list.removeNode(node);
			var prev = node.prev;
			node = prev != null ? prev : (Node<E>) DUMMY;
			removed = true;
		}

		/**
		 * Adds the given entry after the current cursor position
		 * @param entry the entry to add
		 */
		public void add(E entry) {
			var node = this.node;
			list.insertAfter(node == DUMMY ? null : node, entry);
		}
		
		/**
		 * Adds the given entry before the current cursor position
		 * @param entry the entry to add
		 */
		public void addBefore(E entry) {
			var node = this.node;
			list.insertBefore(node == DUMMY ? null : node, entry);
		}
		
	}
	
}
