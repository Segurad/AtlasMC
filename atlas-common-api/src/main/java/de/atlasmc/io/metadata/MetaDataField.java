package de.atlasmc.io.metadata;

import java.util.Objects;

/**
 * Field of {@link MetaData} defines the index, default data and {@link MetaDataType}
 * @param <T>
 */
public class MetaDataField<T> {
	
	private final byte index;
	private final T defaultData;
	private final MetaDataType<T> type;
	
	public MetaDataField(int index, T defautlData, MetaDataType<T> type) {
		this.index = (byte) index;
		this.type = Objects.requireNonNull(type, "type");
		this.defaultData = type.optional ? Objects.requireNonNull(defautlData, "default") : defautlData;
	}
	
	public int getIndex() {
		return index & 0xFF;
	}
	
	public T getDefaultData() {
		return defaultData;
	}
	
	public MetaDataType<T> getType() {
		return type;
	}

	public void validateData(T data) {
		if (data == null && !type.isOptional())
			throw new IllegalArgumentException("Data can not be null for non optional Type!");
		else if (!type.getTypeClass().isInstance(data))
			throw new IllegalArgumentException("Data incompatible with Type!");
	}

}
