package de.atlasmc.node.entity.metadata;

import java.util.Objects;

import de.atlasmc.node.entity.metadata.type.MetaDataType;

public class MetaDataInfo<T>  {
	
	private int index;
	private MetaDataType<T> type;
	private T data;
	
	public MetaDataInfo(MetaData<T> data) {
		var field = data.getField();
		this(field.getIndex(), field.getType(), data.getData());
	}
	
	public MetaDataInfo(int index, MetaDataType<T> type, T data) {
		setIndex(index);
		setType(type);
		setData(data);
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
	
	public void setType(MetaDataType<T> type) {
		this.type = Objects.requireNonNull(type, "type");
	}
	
	public void setIndex(int index) {
		this.index = index & 0xFF;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
}
