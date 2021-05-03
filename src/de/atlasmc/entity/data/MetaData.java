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
	
	public T getData() {
		return data;
	}
	
	public int getIndex() {
		return index;
	}
	
	public MetaDataType<T> getType() {
		return type;
	}
	
	public boolean hasChanged() {
		return changed;
	}
	
	public boolean hasData() {
		return data != null;
	}
	
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	/**
	 * Sets the data and marks it as changed
	 * @param data
	 */
	public void setData(T data) {
		this.data = data;
		changed = true;
	}

}
