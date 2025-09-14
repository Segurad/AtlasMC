package de.atlasmc.util.configuration;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ListMemoryConfigurationSection extends AbstractConfigurationSection implements ListConfigurationSection {

	private final List<Object> values;
	private Map<String, Object> map;
	private Set<String> keySet;
	
	public ListMemoryConfigurationSection(ConfigurationSection parent) {
		super(parent);
		values = new ArrayList<>();
	}
	
	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> map = this.map;
		if (map == null)
			this.map = map = new ValueMap(this);
		return map;
	}

	@Override
	public Set<String> getKeys() {
		Set<String> keySet = this.keySet;
		if (keySet == null)
			this.keySet = keySet = new KeySet(this);
		return keySet;
	}
	
	@Override
	public ConfigurationSection addSection() {
		ConfigurationSection section = createSection();
		values.add(section);
		return section;
	}
	
	@Override
	public ListConfigurationSection addListSection() {
		ListConfigurationSection section = createListSection();
		values.add(section);
		return section;
	}

	@Override
	public void clear() {
		values.clear();
	}

	@Override
	public int size() {
		return values.size();
	}

	@Override
	public boolean isEmpty() {
		return values.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return values.contains(o);
	}

	@Override
	public Iterator<Object> iterator() {
		return values.iterator();
	}

	@Override
	public Object[] toArray() {
		return values.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return values.toArray(a);
	}

	@Override
	public boolean add(Object e) {
		return values.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return values.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return values.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Object> c) {
		return values.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Object> c) {
		return values.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return values.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return values.retainAll(c);
	}

	@Override
	public void add(int index, Object element) {
		values.add(index, element);
	}

	@Override
	public int indexOf(Object o) {
		return values.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return values.lastIndexOf(o);
	}

	@Override
	public ListIterator<Object> listIterator() {
		return values.listIterator();
	}

	@Override
	public ListIterator<Object> listIterator(int index) {
		return values.listIterator(index);
	}

	@Override
	public List<Object> subList(int fromIndex, int toIndex) {
		return values.subList(fromIndex, toIndex);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> asList() {
		return (List<T>) values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> asListOfType(Class<T> clazz) {
		for (Object v : values) {
			if (!clazz.isInstance(v))
				return null;
		}
		return (List<T>) values;
	}

	@Override
	public Object get(int index, Object def) {
		if (index < 0 || values.size() <= index)
			return def;
		Object obj = values.get(index);
		return obj != null ? obj : def;
	}

	@Override
	public Object set(int index, Object value) {
		if (index == values.size()) {
			values.add(value);
			return null;
		}
		return values.set(index, value);
	}

	@Override
	public Object remove(int index) {
		return values.remove(index);
	}

	@Override
	protected Object internalRemove(String key) {
		return remove(Integer.parseInt(key));
	}

	@Override
	protected Object internalGet(String key) {
		return get(Integer.parseInt(key));
	}

	@Override
	protected Object internalSet(String key, Object value) {
		return set(Integer.parseInt(key), value);
	}
	
	private static final class EntrySet extends AbstractSet<Entry<String, Object>> {

		private final ListConfigurationSection section;
		
		public EntrySet(ListConfigurationSection section) {
			this.section = section;
		}
		
		@Override
		public int size() {
			return section.size();
		}

		@Override
		public Iterator<Entry<String, Object>> iterator() {
			return new EntryIterator(section);
		}
		
	}
	
	private static final class EntryIterator implements Iterator<Entry<String, Object>> {
		
		private final ListIterator<Object> iter;
		private final ListConfigurationSection section;
		
		public EntryIterator(ListConfigurationSection section) {
			this.section = section;
			this.iter = section.listIterator();
		}
		
		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public Entry<String, Object> next() {
			int index = iter.nextIndex();
			Object value = iter.next();
			return new SectionEntry(section, index, value);
		}
		
	}
	
	private static final class SectionEntry implements Entry<String, Object> {
		
		private final int index;
		private final ListConfigurationSection section;
		private String stringIndex;
		private Object value;
		
		public SectionEntry(ListConfigurationSection section, int index, Object value) {
			this.section = section;
			this.index = index;
			this.value = value;
		}
		
		@Override
		public String getKey() {
			String val = stringIndex;
			if (val == null)
				this.stringIndex = val = Integer.toString(index);
			return val;
		}

		@Override
		public Object getValue() {
			return value;
		}

		@Override
		public Object setValue(Object value) {
			this.value = value;
			return section.set(index, value);
		}
		
	}
	
	private static final class ValueMap extends AbstractMap<String, Object> {

		private final ListConfigurationSection section;
		
		public ValueMap(ListConfigurationSection section) {
			this.section = section;
		}
		
		@Override
		public Object get(Object key) {
			if (key == null)
				return null;
			if (key instanceof Number number) {
				return section.get(number.intValue());
			}
			return section.get(key.toString());
		}
		
		@Override
		public Set<Entry<String, Object>> entrySet() {
			return new EntrySet(section);
		}

		@Override
		public int size() {
			return section.size();
		}

		@Override
		public boolean containsValue(Object value) {
			return section.contains(value);
		}

		@Override
		public Object put(String key, Object value) {
			return section.set(key, value);
		}

		@Override
		public Object remove(Object key) {
			if (key == null)
				return null;
			if (key instanceof Number number)
				return section.remove(number.intValue());
			return section.remove(key.toString());
		}

		@Override
		public void clear() {
			section.clear();
		}

		@Override
		public Set<String> keySet() {
			return section.getKeys();
		}

		@Override
		public Collection<Object> values() {
			return section.asList();
		}
		
	}
	
	private static final class KeySet extends AbstractSet<String> {
		
		private final ListConfigurationSection section;
		
		public KeySet(ListConfigurationSection section) {
			this.section = section;
		}
		
		@Override
		public int size() {
			return section.size();
		}

		@Override
		public Iterator<String> iterator() {
			return new KeyStringIterator(section.listIterator());
		}
		
	}
	
	private static final class KeyStringIterator implements Iterator<String> {
		
		private final ListIterator<Object> iter;
		
		public KeyStringIterator(ListIterator<Object> iter) {
			this.iter = iter;
		}
		
		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public String next() {
			int index = iter.nextIndex();
			iter.next(); // Step to next element
			return Integer.toString(index);
		}
		
	}

}
