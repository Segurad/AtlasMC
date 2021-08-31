package de.atlasmc.entity.data;

public class MetaDataContainer {
	
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
	
	public MetaData<?> get(int index) {
		return data[index];
	}
	
	/**
	 * Sets the data at it's index and marks it as changed
	 * @param data
	 */
	public void set(MetaData<?> data) {
		this.data[data.getIndex()] = data;
		data.setChanged(true);
	}
	
	public MetaDataType<?> getType(int index) {
		return data[index].getType();
	}

	public MetaData<?>[] getValues() {
		return data;
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
			meta.setChanged(changed);
		}
	}

}
