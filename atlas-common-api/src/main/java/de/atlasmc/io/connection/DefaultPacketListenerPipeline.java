package de.atlasmc.io.connection;

import java.lang.reflect.Array;

import de.atlasmc.io.Packet;

public class DefaultPacketListenerPipeline implements PacketListenerPipeline {

	private static final PacketListener[] EMPTY = {};
	private static final String[] EMPTY_NAMES = {};
	
	private volatile PacketListener[] listeners = EMPTY;
	private volatile String[] names = EMPTY_NAMES;
	
	@Override
	public PacketListener get(String name) {
		PacketListener[] listeners;
		String[] names;
		synchronized (this) {
			listeners = this.listeners;
			names = this.names;
		}
		int index = findIndex(names, name);
		return index != -1 ? listeners[index] : null;
	}

	@Override
	public boolean addFirst(String name, PacketListener listener) {
		if (listener == null)
			throw new NullPointerException("listener");
		var names = this.names;
		var lname = getListenerName(name, listener);
		ensureNotPresent(names, lname);
		var listeners = insertAt(this.listeners, 0, listener);
		names = insertAt(names, 0, lname);
		this.listeners = listeners;
		this.names = names;
		return true;
	}

	@Override
	public synchronized boolean addLast(String name, PacketListener listener) {
		if (listener == null)
			throw new NullPointerException("listener");
		var names = this.names;
		var lname = getListenerName(name, listener);
		ensureNotPresent(names, lname);
		var listeners = insertAt(this.listeners, names.length, listener);
		names = insertAt(names, names.length, lname);
		this.names = names;
		this.listeners = listeners;
		return true;
	}

	@Override
	public boolean addBefore(String before, String name, PacketListener listener) {
		if (before == null)
			throw new NullPointerException("before");
		if (listener == null)
			throw new NullPointerException("listener");
		var names = this.names;
		int index = findIndex(names, before);
		if (index == -1)
			return false;
		var lname = getListenerName(name, listener);
		ensureNotPresent(names, lname);
		var listeners = insertAt(this.listeners, index, listener);
		names = insertAt(names, index, lname);
		this.names = names;
		this.listeners = listeners;
		return true;
	}

	@Override
	public boolean addAfter(String after, String name, PacketListener listener) {
		if (after == null)
			throw new NullPointerException("after");
		if (listener == null)
			throw new NullPointerException("listener");
		var names = this.names;
		int index = findIndex(names, after);
		if (index == -1)
			return false;
		index++;
		var lname = getListenerName(name, listener);
		ensureNotPresent(names, lname);
		var listeners = insertAt(this.listeners, index, listener);
		names = insertAt(names, index, lname);
		this.names = names;
		this.listeners = listeners;
		return true;
	}

	@Override
	public boolean replace(String old, String name, PacketListener listener) {
		if (old == null)
			throw new NullPointerException("old");
		if (listener == null)
			throw new NullPointerException("listener");
		var names = this.names;
		int index = findIndex(names, old);
		if (index == -1)
			return false;
		names = names.clone();
		var listeners = this.listeners.clone();
		names[index] = getListenerName(name, listener);
		listeners[index] = listener;
		this.listeners = listeners;
		this.names = names;
		return true;
	}

	@Override
	public boolean remove(PacketListener listener) {
		var listeners = this.listeners;
		if (listeners.length == 0)
			return false;
		int index = findIndex(listeners, listener);
		if (index == -1)
			return false;
		this.listeners = removeAt(listeners, index, EMPTY);
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
		this.listeners = removeAt(this.listeners, index, EMPTY);
		this.names = removeAt(names, index, EMPTY_NAMES);
		return true;
	}
	
	private <T> T[] insertAt(T[] array, int i, T value) {
		final int length = array.length;
		@SuppressWarnings("unchecked")
		T[] newArray = (T[]) Array.newInstance(array.getClass(), length + 1);
		if (i > 0)
			System.arraycopy(array, 0, newArray, 0, i);
		newArray[i] = value;
		if (i < length)
			System.arraycopy(array, i, newArray, i + 1, length - i);
		return newArray;
	}
	
	private <T> T[] removeAt(T[] array, int i, T[] empty) {
		final int length = array.length;
		if (length == 1)
			return empty;
		@SuppressWarnings("unchecked")
		T[] newArray = (T[]) Array.newInstance(array.getClass(), length - 1);
		if (i > 0)
			System.arraycopy(array, 0, newArray, 0, i);
		if (i != length - 1)
			System.arraycopy(array, i + 1, newArray, i, length - 1 - i);
		return newArray;
	}

	@Override
	public synchronized boolean removeListeners() {
		if (listeners == EMPTY)
			return false;
		listeners = EMPTY;
		names = EMPTY_NAMES;
		return true;
	}

	@Override
	public boolean hasListeners() {
		return listeners != EMPTY;
	}

	@Override
	public int getCount() {
		return listeners.length;
	}
	
	private String getListenerName(String name, PacketListener listener) {
		return name != null ? name : "Listener-" + listener.getClass().getName();
	}
	
	private <T> int findIndex(T[] names, T name) {
		final var length = names.length;
		for (int i = 0; i < length; i++) {
			if (names[i].equals(name))
				return i;
		}
		return -1;
	}
	
	private void ensureNotPresent(String[] names, String name) {
		if (findIndex(names, name) != -1)
			throw new IllegalArgumentException("Listener with name: " + name + " already present!");
	}

	@Override
	public void handlePacket(final ConnectionHandler handler, final Packet packet) {
		final var listeners = this.listeners;
		final int count = listeners.length;
		if (count == 0)
			return;
		for (int i = 0; i < count; i++) {
			final PacketListener listener = listeners[i];
			try {
				listener.handlePacketSync(handler, packet);
			} catch (Exception e) {
				handler.handleException(e);
			}
		}
	}

}
