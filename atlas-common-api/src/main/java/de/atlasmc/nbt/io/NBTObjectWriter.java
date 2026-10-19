package de.atlasmc.nbt.io;

import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.function.LongSupplier;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.tag.CompoundTag;
import de.atlasmc.nbt.tag.ListTag;
import de.atlasmc.nbt.tag.NBT;

/**
 * {@link NBTWriter} that builds {@link NBT} objects.
 * Follows the same contract as the stream writers: 
 * elements of a {@link TagType#COMPOUND} list are opened with {@link #writeCompoundTag()} and closed with {@link #writeEndTag()},
 * lists are closed automatically once the given payload size is reached.
 */
public class NBTObjectWriter extends AbstractNBTWriter implements NBTWriter {
	
	private final Deque<NBT> containers = new ArrayDeque<>();
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
		if (highestContainer.getType() == TagType.LIST)
			throw new NBTException("Can not write EndTag to List");
		highestContainer = containers.pop();
		closeCompletedLists();
	}
	
	/**
	 * Adds the given tag to the current container and closes all lists that reached their payload size
	 * @param tag
	 */
	private void addTag(NBT tag) {
		if (highestContainer instanceof ListTag list && list.getPayloadSize() >= list.getExspectedPayloadSize())
			throw new NBTException("Max list payload reached!");
		highestContainer.setData(tag);
		closeCompletedLists();
	}
	
	private void closeCompletedLists() {
		while (highestContainer != masterContainer 
				&& highestContainer instanceof ListTag list 
				&& list.getPayloadSize() >= list.getExspectedPayloadSize()) {
			highestContainer = containers.pop();
		}
	}
	
	private void openContainer(NBT tag) {
		if (highestContainer instanceof ListTag list && list.getPayloadSize() >= list.getExspectedPayloadSize())
			throw new NBTException("Max list payload reached!");
		highestContainer.setData(tag);
		containers.push(highestContainer);
		highestContainer = tag;
	}

	private String getNameValue(CharSequence name) {
		if (highestContainer.getType() == TagType.LIST)
			return null;
		return name != null ? name.toString() : null;
	}

	@Override
	public void writeByteTag(CharSequence name, int value) throws IOException {
		ensureOpen();
		addTag(NBT.createByteTag(getNameValue(name), value));
	}

	@Override
	public void writeShortTag(CharSequence name, int value) throws IOException {
		ensureOpen();
		addTag(NBT.createShortTag(getNameValue(name), value));
	}

	@Override
	public void writeIntTag(CharSequence name, int value) throws IOException {
		ensureOpen();
		addTag(NBT.createIntTag(getNameValue(name), value));
	}

	@Override
	public void writeLongTag(CharSequence name, long value) throws IOException {
		ensureOpen();
		addTag(NBT.createLongTag(getNameValue(name), value));
	}

	@Override
	public void writeFloatTag(CharSequence name, float value) throws IOException {
		ensureOpen();
		addTag(NBT.createFloatTag(getNameValue(name), value));
	}

	@Override
	public void writeDoubleTag(CharSequence name, double value) throws IOException {
		ensureOpen();
		addTag(NBT.createDoubleTag(getNameValue(name), value));
	}

	@Override
	public void writeByteArrayTag(CharSequence name, byte[] data, int offset, int length) throws IOException {
		ensureOpen();
		Objects.requireNonNull(data, "data");
		addTag(NBT.createByteArrayTag(getNameValue(name), Arrays.copyOfRange(data, offset, offset + length)));
	}

	@Override
	public void writeStringTag(CharSequence name, String value) throws IOException {
		ensureOpen();
		Objects.requireNonNull(value, "value");
		ensureStringLength(value);
		addTag(NBT.createStringTag(getNameValue(name), value));
	}

	@Override
	public void writeListTag(CharSequence name, TagType payload, int payloadsize) throws IOException {
		ensureOpen();
		Objects.requireNonNull(payload, "payload");
		NBT tag = NBT.createListTag(getNameValue(name), payload, payloadsize);
		if (payloadsize <= 0) {
			addTag(tag);
		} else {
			openContainer(tag);
		}
	}

	@Override
	public void writeCompoundTag(CharSequence name) throws IOException {
		ensureOpen();
		NBT tag = NBT.createCompoundTag(getNameValue(name));
		openContainer(tag);
	}
	
	@Override
	public void writeIntArrayTag(CharSequence name, int[] data, int offset, int length) throws IOException {
		ensureOpen();
		Objects.requireNonNull(data, "data");
		addTag(NBT.createIntArrayTag(getNameValue(name), Arrays.copyOfRange(data, offset, offset + length)));
	}
	
	@Override
	public void writeLongArrayTag(CharSequence name, long[] data, int offset, int length) throws IOException {
		ensureOpen();
		Objects.requireNonNull(data, "data");
		addTag(NBT.createLongArrayTag(getNameValue(name), Arrays.copyOfRange(data, offset, offset + length)));
	}
	
	@Override
	public void writeLongArrayTag(CharSequence name, int length, LongSupplier supplier) throws IOException {
		ensureOpen();
		Objects.requireNonNull(supplier, "supplier");
		long[] data = new long[length];
		for (int i = 0; i < length; i++)
			data[i] = supplier.getAsLong();
		addTag(NBT.createLongArrayTag(getNameValue(name), data));
	}

	@Override
	public void writeNBT(NBT nbt) throws IOException {
		ensureOpen();
		addTag(nbt.clone());
	}
	
	@Override
	public void writeNBT(CharSequence name, NBT nbt) throws IOException {
		ensureOpen();
		NBT copy = nbt.clone();
		copy.setName(name != null ? name.toString() : null);
		addTag(copy);
	}

	public NBT toNBT() {
		return masterContainer;
	}

	@Override
	public void close() throws IOException {
		super.close();
		containers.clear();
		highestContainer = null;
		masterContainer = null;
	}

}
