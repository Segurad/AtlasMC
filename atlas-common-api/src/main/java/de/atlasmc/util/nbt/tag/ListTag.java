package de.atlasmc.util.nbt.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public final class ListTag extends AbstractCollectionTag<ListTag, List<NBT>> implements Iterable<NBT> {

	private TagType datatype;
	private int exspected;
	
	public ListTag(String name, TagType datatype) {
		this(name, datatype, 0);
	}
	
	public ListTag(String name, TagType datatype, int payloadsize) {
		super(name);
		this.datatype = datatype;
		this.exspected = payloadsize;
	}

	public ListTag() {
		this.exspected = 0;
	}
	
	public int getPayloadSize() {
		return data != null ? data.size() : 0;
	}
	
	public int getExspectedPayloadSize() {
		return exspected;
	}
	
	/**
	 * 
	 * @param field
	 * @return this
	 */
	public ListTag createEntry(Object field) {
		NBT tag = datatype.createTag(field);
		add(tag);
		return this;
	}
	
	public TagType getDataType() {
		return datatype;
	}

	@Override
	public TagType getType() {
		return TagType.LIST;
	}

	public int size() {
		return data.size();
	}
	
	@Override
	protected void add(NBT data) {
		if (data == null) 
			throw new IllegalArgumentException("NBT can not be null!");
		if (datatype != data.getType()) 
			throw new IllegalArgumentException("Illegal TagType:" + data.getType().name() + " " + datatype.name() + " expected!");
		super.add(data);
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeListTag(name, datatype, data != null ? data.size() : 0);
		if (data != null && !data.isEmpty())
			for (NBT element : data)
				writer.writeNBT(element);
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		if (reader.getType() != TagType.LIST)
			throw new NBTException("Can not read " + reader.getType().name() + " as LIST");
		CharSequence name = reader.getFieldName();
		if (name != null)
			this.name = name.toString();
		datatype = reader.getListType();
		exspected = reader.getRestPayload();
		reader.readNextEntry();
		while (reader.getRestPayload() > 0)
			addTag(reader.readNBT());
	}

	@Override
	protected List<NBT> createCollection() {
		return new ArrayList<>();
	}

	@Override
	protected ListTag getThis() {
		return this;
	}
	
}
