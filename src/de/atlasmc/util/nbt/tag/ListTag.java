package de.atlasmc.util.nbt.tag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.atlasmc.util.nbt.TagType;

public final class ListTag<T extends NBT> extends AbstractTag implements Iterable<NBT> {

	private List<T> data;
	private TagType datatype;
	private final int exspected;
	
	public ListTag(String name, TagType datatype) {
		data = new ArrayList<>();
		this.datatype = datatype;
		this.name = name;
		this.exspected = 0;
	}
	
	public ListTag(String name, TagType datatype, int payloadsize) {
		data = new ArrayList<>(payloadsize);
		this.datatype = datatype;
		this.name = name;
		this.exspected = payloadsize;
	}

	public ListTag() {
		this.exspected = 0;
	}

	@Override
	public List<T> getData() {
		return data;
	}
	
	public int getPayloadSize() {
		return data != null ? data.size() : 0;
	}
	
	public int getExspectedPayloadSize() {
		return exspected;
	}
	
	public void addTag(T tag) {
		if (tag == null) 
			throw new IllegalArgumentException("NBT can not be null!");
		if (datatype != tag.getType()) 
			throw new IllegalArgumentException("Illegal TagType:" + tag.getType().name() + " " + datatype.name() + " expected!");
		data.add(tag);
	}
	
	@SuppressWarnings("unchecked")
	public T createEntry(Object field) {
		T tag = (T) datatype.createTag(field);
		data.add(tag);
		return tag;
	}
	
	public TagType getDataType() {
		return datatype;
	}

	@Override
	public TagType getType() {
		return TagType.LIST;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setData(Object data) {
		NBT tag = (NBT) data;
		if (datatype != tag.getType()) throw new IllegalArgumentException("Illegal TagType:" + tag.getType().name() + " " + datatype.name() + " expected!");
		addTag((T) tag);
	}

	public int size() {
		return data.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<NBT> iterator() {
		return (Iterator<NBT>) data.iterator();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ListTag<T> clone() {
		ListTag<T> clone = (ListTag<T>) super.clone();
		if (clone == null)
			return null;
		if (data != null) {
			List<NBT> list = new ArrayList<NBT>(data.size());
			for (NBT nbt : data) {
				list.add(nbt.clone());
			}
			clone.setData(list);
		}
		return clone;
	}

}
