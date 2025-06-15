package de.atlasmc.tag;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.annotation.NotNull;

public class Tag<T> implements Namespaced {
	
	protected final Class<?> type;
	protected final Set<T> values;
	private Set<T> view;
	protected final NamespacedKey identifier;
	
	public Tag(NamespacedKey identifier, Class<?> type) {
		if (identifier == null)
			throw new IllegalArgumentException("Identifier can not be null!");
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		this.identifier = identifier;
		this.values = ConcurrentHashMap.newKeySet();
		this.type = type;
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
	
}
