package de.atlasmc.util.pipeline;

import java.util.Objects;

public abstract class AbstractConcurrentPipeline<E> extends AbstractPipeline<E> {

	protected volatile E[] entries = getEmpty();
	protected volatile String[] names = EMPTY_NAMES;
	
	@Override
	public E get(String name) {
		Objects.requireNonNull(name, "name");
		E[] entries;
		String[] names;
		synchronized (this) {
			entries = this.entries;
			names = this.names;
		}
		int index = findIndex(names, name);
		return index != -1 ? entries[index] : null;
	}

	@Override
	public synchronized boolean addFirst(String name, E entry) {
		Objects.requireNonNull(entry, "entry");
		var names = this.names;
		var eName = getEntryName(name, entry);
		ensureNotPresent(names, eName);
		E[] entries = insertAt(this.entries, 0, entry);
		names = insertAt(names, 0, eName);
		this.entries = entries;
		this.names = names;
		return true;
	}

	@Override
	public synchronized boolean addLast(String name, E entry) {
		Objects.requireNonNull(entry, "entry");
		var names = this.names;
		var eName = getEntryName(name, entry);
		ensureNotPresent(names, eName);
		var entries = insertAt(this.entries, names.length, entry);
		names = insertAt(names, names.length, eName);
		this.entries = entries;
		this.names = names;
		return true;
	}

	@Override
	public synchronized boolean addBefore(String before, String name, E entry) {
		Objects.requireNonNull(before, "before");
		Objects.requireNonNull(entry, "entry");
		var names = this.names;
		int index = findIndex(names, before);
		if (index == -1)
			return false;
		var eName = getEntryName(name, entry);
		ensureNotPresent(names, eName);
		var entries = insertAt(this.entries, index, entry);
		names = insertAt(names, index, eName);
		this.entries = entries;
		this.names = names;
		return true;
	}

	@Override
	public synchronized boolean addAfter(String after, String name, E entry) {
		Objects.requireNonNull(after, "after");
		Objects.requireNonNull(entry, "entry");
		var names = this.names;
		int index = findIndex(names, after);
		if (index == -1)
			return false;
		index++;
		var eName = getEntryName(name, entry);
		ensureNotPresent(names, eName);
		var entries = insertAt(this.entries, index, entry);
		names = insertAt(names, index, eName);
		this.entries = entries;
		this.names = names;
		return true;
	}

	@Override
	public synchronized boolean replace(String old, String name, E entry) {
		Objects.requireNonNull(old, "old");
		Objects.requireNonNull(entry, "entry");
		var names = this.names;
		int index = findIndex(names, old);
		if (index == -1)
			return false;
		var eName = getEntryName(name, entry);
		ensureNotPresent(names, eName);
		names = names.clone();
		var entries = this.entries.clone();
		names[index] = eName;
		entries[index] = entry;
		this.entries = entries;
		this.names = names;
		return true;
	}

	@Override
	public synchronized boolean remove(E entry) {
		var entries = this.entries;
		if (entries.length == 0)
			return false;
		int index = findIndex(entries, entry);
		if (index == -1)
			return false;
		this.entries = removeAt(entries, index, getEmpty());
		this.names = removeAt(names, index, EMPTY_NAMES);
		return true;
	}

	@Override
	public synchronized boolean remove(String name) {
		var names = this.names;
		if (names.length == 0)
			return false;
		int index = findIndex(names, name);
		if (index == -1)
			return false;
		this.entries = removeAt(this.entries, index, getEmpty());
		this.names = removeAt(names, index, EMPTY_NAMES);
		return true;
	}

	@Override
	public synchronized boolean clear() {
		if (entries.length == 0)
			return false;
		entries = getEmpty();
		names = EMPTY_NAMES;
		return true;
	}
	
	protected abstract E[] getEmpty();
	
	protected String getEntryName(String name, E entry) {
		return name != null ? name : "Entry-" + entry.getClass().getName();
	}
	
	@Override
	public boolean contains(E entry) {
		Objects.requireNonNull(entry, "entry");
		return findIndex(entries, entry) != -1;
	}
	
	@Override
	public boolean contains(String name) {
		Objects.requireNonNull(name, "name");
		return findIndex(names, name) != -1;
	}

	@Override
	public int size() {
		return entries.length;
	}
	
}
