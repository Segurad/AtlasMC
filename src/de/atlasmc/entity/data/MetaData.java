package de.atlasmc.entity.data;

public class MetaData<T> {

	private T data;
	private boolean changed;
	private final MetaDataField<T> field;
	
	public MetaData(MetaDataField<T> field) {
		this.field = field;
		data = field.getDefaultData();
	}
	
	public MetaData(MetaDataField<T> field, T data) {
		this.field = field;
		this.data = data;
	}
	
	public T getData() {
		return data;
	}
	
	public int getIndex() {
		return field.getIndex();
	}
	
	public MetaDataType<T> getType() {
		return field.getType();
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
	 * Returns whether or not the data is equals to the fields default data
	 * @return true if default
	 */
	public boolean isDefault() {
		T defaultData = field.getDefaultData();
		if (data == null)
			return defaultData == null;
		return data.equals(defaultData);
	}
	
	/**
	 * Sets the data and marks it as changed if it has changed
	 * @param data
	 * @return true if changed
	 */
	public boolean setData(T data) {
		if (this.data == data)
			return false;
		field.validateData(data);
		this.data = data;
		changed = true;
		return true;
	}

}
