package de.atlasmc.entity.data;

import java.util.Iterator;

import de.atlasmc.util.iterator.ArrayIterator;

public class MetaDataContainer implements Iterable<MetaData<?>> {
	
	private final MetaData<?>[] data;
	
	public MetaDataContainer(int size) {
		data = new MetaData<?>[size];
	}
	
	public int size() {
		return data.length;
	}
	
	@SuppressWarnings("unchecked")
	public <T> MetaData<T> get(int index, MetaDataType<T> type) {
		return (MetaData<T>) data[index];
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getData(int index, MetaDataType<T> type) {
		return (T) data[index].getData();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getData(MetaDataField<T> field) {
		return (T) data[field.getIndex()].getData();
	}
	
	@SuppressWarnings("unchecked")
	public MetaData<Object> get(int index) {
		return (MetaData<Object>) data[index];
	}
	
	@SuppressWarnings("unchecked")
	public <T> MetaData<T> get(MetaDataField<T> field) {
		return (MetaData<T>) data[field.getIndex()];
	}
	
	/**
	 * Sets the data at it's index and marks it as changed.
	 * @param data
	 * @return the set {@link MetaData}
	 */
	public <T> MetaData<T> set(MetaData<T> data) {
		this.data[data.getIndex()] = data;
		data.setChanged(true);
		return data;
	}
	
	public MetaDataType<?> getType(int index) {
		return data[index].getType();
	}
	
	/**
	 * 
	 * @return true one entry has changed
	 */
	public boolean hasChanges() {
		for (MetaData<?> meta : data) {
			if (meta.hasChanged()) return true;
		}
		return false;
	}
	
	/**
	 * Sets the changed value for all entries
	 * @param changed
	 */
	public void setChanged(boolean changed) {
		for (MetaData<?> meta : data) {
			if (meta == null)
				continue;
			meta.setChanged(changed);
		}
	}

	/**
	 * Sets a new {@link MetaData} for the {@link MetaDataField} with its default value and marks it as changed
	 * @param <T>
	 * @param field
	 * @return the new {@link MetaData}
	 */
	public <T> MetaData<T> set(MetaDataField<T> field) {
		return set(new MetaData<>(field));
	}
	
	/**
	 * Sets a new {@link MetaData} for the {@link MetaDataField} with the given data value and marks it as changed
	 * @param <T>
	 * @param field
	 * @param data
	 * @return the new {@link MetaData}
	 */
	public <T> MetaData<T> set(MetaDataField<T> field, T data) {
		return set(new MetaData<>(field, data));
	}

	@Override
	public Iterator<MetaData<?>> iterator() {
		return new ArrayIterator<>(data, false);
	}

}
