package de.atlasmc.io.metadata;

import java.util.Objects;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

/**
 * Stores data of a {@link MetaData}, keeps track of changes and contains {@link MetaDataField} information
 * @param <T>
 */
public class MetaData<T> {

	private T data;
	boolean changed;
	private final MetaDataField<T> field;
	
	public MetaData(MetaDataField<T> field) {
		this(field, field.getType().copyData(field.getDefaultData()));
	}
	
	public MetaData(MetaDataField<T> field, T data) {
		this.field = Objects.requireNonNull(field, "field");
		setData(data);
		changed = false;
	}
	
	@NotNull
	public MetaDataField<T> getField() {
		return field;
	}
	
	/**
	 * Returns the value of this meta data. 
	 * @return value
	 */
	@Nullable
	public T getData() {
		return data;
	}
	
	public boolean hasChanged() {
		return changed;
	}
	
	public boolean hasData() {
		return data != null;
	}
	
	/**
	 * Returns whether or not the data is equals to the fields default data
	 * @return true if default
	 */
	public boolean isDefault() {
		T defaultData = field.getDefaultData();
		return Objects.equals(this.data, defaultData);
	}
	
	/**
	 * Sets the data and marks it as changed if data.equals({@link #getData()}) == false
	 * @param data
	 * @return true if changed
	 */
	boolean setData(T data) {
		if (!Objects.equals(this.data, data))
			return false;
		field.validateData(data);
		this.data = data;
		changed = true;
		return true;
	}
	
	public MetaDataInfo<T> info() {
		return new MetaDataInfo<>(this);
	}

}
