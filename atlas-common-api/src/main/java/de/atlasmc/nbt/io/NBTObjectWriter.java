package de.atlasmc.nbt.io;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.LongSupplier;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.tag.CompoundTag;
import de.atlasmc.nbt.tag.ListTag;
import de.atlasmc.nbt.tag.NBT;

public class NBTObjectWriter extends AbstractNBTWriter implements NBTWriter {
	
	private LinkedList<NBT> containers;
	private NBT highestContainer;
	private NBT masterContainer;
	
	/**
	 * Creates a new NBTObjectWirter with a empty {@link CompoundTag} as root 
	 */
	public NBTObjectWriter() {
		this(new CompoundTag());
	}
	
	/**
	 * Creates a new NBTObjectWirter with the given NBT as root
	 * @param root root of this writer
	 */
	public NBTObjectWriter(NBT root) {
		highestContainer = Objects.requireNonNull(root, "root");
		masterContainer = highestContainer;
	}

	@Override
	public void writeEndTag() throws IOException {
		ensureOpen();
		if (highestContainer == masterContainer) 
			throw new NBTException("No NBT to close available!");
		highestContainer = containers.poll();
		if (highestContainer.getType() != TagType.LIST)
			return;
		ListTag list = (ListTag) highestContainer;
		if (list.getExspectedPayloadSize() > list.getPayloadSize()) 
			writeCompoundTag();
		else
			writeEndTag();
	}

	private String getNameValue(CharSequence name) {
		if (highestContainer.getType() == TagType.LIST)
			return null;
		return name != null ? name.toString() : null;
	}

	@Override
	public void writeByteTag(CharSequence name, int value) throws IOException {
		ensureOpen();
		highestContainer.setData(NBT.createByteTag(getNameValue(name), value));
	}

	@Override
	public void writeShortTag(CharSequence name, int value) throws IOException {
		ensureOpen();
		highestContainer.setData(NBT.createShortTag(getNameValue(name), value));
	}

	@Override
	public void writeIntTag(CharSequence name, int value) throws IOException {
		ensureOpen();
		highestContainer.setData(NBT.createIntTag(getNameValue(name), value));
	}

	@Override
	public void writeLongTag(CharSequence name, long value) throws IOException {
		ensureOpen();
		highestContainer.setData(NBT.createLongTag(getNameValue(name), value));
	}

	@Override
	public void writeFloatTag(CharSequence name, float value) throws IOException {
		ensureOpen();
		highestContainer.setData(NBT.createFloatTag(getNameValue(name), value));
	}

	@Override
	public void writeDoubleTag(CharSequence name, double value) throws IOException {
		ensureOpen();
		highestContainer.setData(NBT.createDoubleTag(getNameValue(name), value));
	}

	@Override
	public void writeByteArrayTag(CharSequence name, byte[] data, int offset, int length) throws IOException {
		ensureOpen();
		Objects.requireNonNull(data, "data");
		highestContainer.setData(NBT.createByteArrayTag(getNameValue(name), Arrays.copyOfRange(data, offset, offset + length)));
	}

	@Override
	public void writeStringTag(CharSequence name, String value) throws IOException {
		ensureOpen();
		highestContainer.setData(NBT.createStringTag(getNameValue(name), value));
	}

	@Override
	public void writeListTag(CharSequence name, TagType payload, int payloadsize) throws IOException {
		ensureOpen();
		NBT tag = NBT.createListTag(getNameValue(name), payload, payloadsize);
		if (containers == null)
			containers = new LinkedList<>();
		containers.add(highestContainer);
		highestContainer.setData(tag);
		highestContainer = tag;
		if (payload == TagType.COMPOUND)
			writeCompoundTag();
	}

	@Override
	public void writeCompoundTag(CharSequence name) throws IOException {
		ensureOpen();
		NBT tag = NBT.createCompoundTag(getNameValue(name));
		if (containers == null)
			containers = new LinkedList<>();
		containers.add(highestContainer);
		highestContainer.setData(tag);
		highestContainer = tag;
	}
	
	@Override
	public void writeIntArrayTag(CharSequence name, int[] data, int offset, int length) throws IOException {
		ensureOpen();
		Objects.requireNonNull(data, "data");
		highestContainer.setData(NBT.createIntArrayTag(getNameValue(name), Arrays.copyOfRange(data, offset, offset + length)));
	}
	
	@Override
	public void writeLongArrayTag(CharSequence name, long[] data, int offset, int length) throws IOException {
		ensureOpen();
		Objects.requireNonNull(data, "data");
		highestContainer.setData(NBT.createLongArrayTag(getNameValue(name), Arrays.copyOfRange(data, offset, offset + length)));
	}
	
	@Override
	public void writeLongArrayTag(CharSequence name, int length, LongSupplier supplier) throws IOException {
		ensureOpen();
		Objects.requireNonNull(supplier, "supplier");
		long[] data = new long[length];
		for (int i = 0; i < length; i++)
			data[i] = supplier.getAsLong();
		highestContainer.setData(NBT.createLongArrayTag(getNameValue(name), data));
	}

	@Override
	public void writeNBT(NBT nbt) throws IOException {
		ensureOpen();
		highestContainer.setData(nbt.clone());
	}
	
	@Override
	public void writeNBT(CharSequence name, NBT nbt) throws IOException {
		ensureOpen();
		NBT copy = nbt.clone();
		copy.setName(name != null ? name.toString() : null);
		highestContainer.setData(copy);
	}

	public NBT toNBT() {
		return masterContainer;
	}

	@Override
	public void close() throws IOException {
		super.close();
		containers = null;
		highestContainer = null;
		masterContainer = null;
	}

}
