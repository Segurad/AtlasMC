package de.atlasmc.entity.data;

public class MetaData<T> {
	
	private final int index;
	private T data;
	private boolean changed;
	private final MetaDataType<T> type;
	
	public MetaData(int index, MetaDataType<T> type) {
		this.index = index;
		this.type = type;
	}
	
	public MetaData(int index, MetaDataType<T> type, T data) {
		this(index, type);
		this.data = data;
	}
	
	public MetaDataType<T> getType() {
		return type;
	}
	
	public int getIndex() {
		return index;
	}
	
	public T getData() {
		return data;
	}
	
	/**
	 * Sets the data and marks it as changed
	 * @param data
	 */
	public void setData(T data) {
		this.data = data;
		changed = true;
	}
	
	public boolean hasChanged() {
		return changed;
	}
	
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

}
