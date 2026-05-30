package de.atlasmc.tag;

import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.annotation.NotNull;

public class Tag<T> implements Namespaced, Iterable<T> {
	
	protected final Class<?> type;
	protected final Set<T> values;
	private Set<T> view;
	protected final NamespacedKey identifier;
	
	public Tag(@NotNull NamespacedKey identifier, @NotNull Class<?> type) {
		this.identifier = Objects.requireNonNull(identifier, "identifier");
		this.values = ConcurrentHashMap.newKeySet();
		this.type = Objects.requireNonNull(type, "type");
	}
	
	@Override
	public final NamespacedKey getNamespacedKey() {
		return identifier;
	}
	
	@NotNull
	public Class<?> getType() {
		return type;
	}
	
	public boolean isTaged(Object element) {
		return values.contains(element);
	}
	
	@NotNull
	public Set<T> getValues() {
		Set<T> view = this.view;
		if (view == null)
			view = this.view = Collections.unmodifiableSet(values);
		return view;
	}
	
	public boolean add(T element) {
		if (element == null)
			throw new IllegalArgumentException("Element can not be null!");
		if (type.isInstance(element))
			throw new IllegalArgumentException("Element is not compatiple with this tag(" + type.getName() + "): " + element.getClass().getName());
		return values.add(element);
	}
	
	public boolean remove(T element) {
		return values.remove(element);
	}

	public int size() {
		return values.size();
	}

	public boolean isEmpty() {
		return values.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		return getValues().iterator();
	}
	
}
