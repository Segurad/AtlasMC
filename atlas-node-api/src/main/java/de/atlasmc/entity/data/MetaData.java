package de.atlasmc.entity.data;

import de.atlasmc.util.CloneException;

/**
 * Stores data of a {@link MetaData}, keeps track of changes and contains {@link MetaDataField} information
 * @param <T>
 */
public class MetaData<T> implements Cloneable {

	private T data;
	private boolean changed;
	private final MetaDataField<T> field;
	
	public MetaData(MetaDataField<T> field) {
		this(field, field.getType().copyData(field.getDefaultData()));
	}
	
	public MetaData(MetaDataField<T> field, T data) {
		this.field = field;
		this.data = data;
	}
	
	/**
	 * Returns the value of this meta data. 
	 * If the value is mutable and was modified {@link #setChanged(boolean)} must be called manually.
	 * @return value
	 */
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
	 * Sets the data and marks it as changed if data.equals({@link #getData()}) == false
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
	
	@SuppressWarnings("unchecked")
	public MetaData<T> clone() {
		MetaData<T> clone = null;
		try {
			clone = (MetaData<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new CloneException();
		}
		if (data != null)
			clone.data = field.getType().copyData(data);
		return clone;
	}

}
