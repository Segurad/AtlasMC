package de.atlasmc.io.metadata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.iterator.ArrayIterator;

/**
 * Stores {@link MetaData} and provides easy access 
 */
public class MetaDataContainer implements Iterable<MetaData<?>> {
	
	private final MetaData<?>[] data;
	private boolean changed;
	
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
	
	public <T> boolean setData(MetaDataField<T> field, T data) {
		@SuppressWarnings("unchecked")
		var metaData = (MetaData<T>) this.data[field.getIndex()];
		var changed = metaData.setData(data);
		if (changed)
			this.changed = true;
		return changed;
	}
	
	public <T> boolean resetData(MetaDataField<T> field) {
		@SuppressWarnings("unchecked")
		var metaData = (MetaData<T>) this.data[field.getIndex()];
		if (metaData.isDefault())
			return false;
		var changed = metaData.setData(field.getType().copyData(field.getDefaultData()));
		if (changed)
			this.changed = true;
		return changed;
	}
	
	public <T> boolean setData(MetaDataField<T> field, T data, boolean changed) {
		@SuppressWarnings("unchecked")
		var metaData = (MetaData<T>) this.data[field.getIndex()];
		metaData.setData(data);
		metaData.changed = changed;
		if (changed)
			this.changed = true;
		return changed;
	}
	
	public void setChanged(MetaDataField<?> field) {
		var metaData = this.data[field.getIndex()];
		metaData.changed = changed;
		if (changed)
			this.changed = true;
	}
	
	/**
	 * Sets the data at it's index and marks it as changed.
	 * @param data
	 * @return the set {@link MetaData}
	 */
	private <T> MetaData<T> set(MetaData<T> data) {
		this.data[data.getField().getIndex()] = data;
		data.changed = true;
		changed = true;
		return data;
	}

	/**
	 * 
	 * @return true one entry has changed
	 */
	public boolean hasChanges() {
		return changed;
	}
	
	/**
	 * Sets the changed value for all entries
	 * @param changed
	 */
	public void setUnchanged() {
		if (!this.changed)
			return;
		for (MetaData<?> meta : data) {
			if (meta == null)
				continue;
			meta.changed = false;
		}
		changed = false;
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
	
	public boolean setMetaData(List<MetaDataInfo<Object>> info, boolean changed) {
		boolean change = false;
		for (var entry : info) {
			@SuppressWarnings("unchecked")
			var metaData = (MetaData<Object>) this.data[entry.getIndex()];
			var dchanged = metaData.setData(entry.getData());
			if (dchanged)
				change = true;
		}
		if (change && changed)
			this.changed = true;
		return change;
	}
	
	/**
	 * Gets all data that is not the default value. Returns null if no non default data.
	 * @param data or null
	 */
	@Nullable
	public List<MetaDataInfo<Object>> getNonDefaultData() {
		List<MetaDataInfo<Object>> data = null;
		for (var meta : this.data) {
			if (meta.isDefault())
				continue;
			if (data == null)
				data = new ArrayList<>();
			@SuppressWarnings("unchecked")
			var info = (MetaDataInfo<Object>) meta.info();
			data.add(info);
		}
		return data;
	}
	
	/**
	 * Gets all data that is changed. Returns null if not changed.
	 * @return data or null
	 */
	@Nullable
	public List<MetaDataInfo<Object>> getChangedData() {
		if (!changed)
			return null;
		var data = new ArrayList<MetaDataInfo<Object>>();
		for (MetaData<?> meta : this.data) {
			if (!meta.changed)
				continue;
			@SuppressWarnings("unchecked")
			var info = (MetaDataInfo<Object>) meta.info();
			data.add(info);
		}
		return data;
	}

	@Override
	public Iterator<MetaData<?>> iterator() {
		return new ArrayIterator<>(data, false);
	}

}
