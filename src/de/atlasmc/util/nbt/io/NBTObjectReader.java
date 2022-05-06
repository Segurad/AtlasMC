package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.util.UUID;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.NBT;

public class NBTObjectReader implements NBTReader {

	private NBT nbt, current;
	private ListData list;
	private boolean closed;
	private int depth;
	
	public NBTObjectReader(NBT nbt) {
		this.nbt = nbt;
		this.current = nbt;
	}

	@Override
	public TagType getType() {
		return current.getType();
	}

	@Override
	public TagType getListType() {
		return list != null ? list.type : null;
	}

	@Override
	public int getRestPayload() {
		return list != null ? list.payload : 0;
	}

	@Override
	public String getFieldName() {
		return current != null ? current.getName() : null;
	}

	@Override
	public byte readByteTag() throws IOException {
		ensureOpen();
		if (current.getType() != TagType.BYTE)
			throw new NBTException("Can not read as ByteTag: " + current.getType().name());
		return (byte) current.getData();
	}

	@Override
	public short readShortTag() throws IOException {
		ensureOpen();
		if (current.getType() != TagType.SHORT)
			throw new NBTException("Can not read as ShortTag: " + current.getType().name());
		return (short) current.getData();
	}

	@Override
	public int readIntTag() throws IOException {
		ensureOpen();
		if (current.getType() != TagType.INT)
			throw new NBTException("Can not read as IntTag: " + current.getType().name());
		return (int) current.getData();
	}

	@Override
	public long readLongTag() throws IOException {
		ensureOpen();
		if (current.getType() != TagType.LONG)
			throw new NBTException("Can not read as LongTag: " + current.getType().name());
		return (long) current.getData();
	}

	@Override
	public float readFloatTag() throws IOException {
		ensureOpen();
		if (current.getType() != TagType.FLOAT)
			throw new NBTException("Can not read as FloatTag: " + current.getType().name());
		return (float) current.getData();
	}

	@Override
	public double readDoubleTag() throws IOException {
		ensureOpen();
		if (current.getType() != TagType.DOUBLE)
			throw new NBTException("Can not read as DoubleTag: " + current.getType().name());
		return (double) current.getData();
	}

	@Override
	public void readByteArrayTag(IntConsumer dataConsumer) throws IOException {
		ensureOpen();
		if (current.getType() != TagType.BYTE_ARRAY)
			throw new NBTException("Can not read as ByteArrayTag: " + current.getType().name());
		byte[] data = (byte[]) current.getData();
		for (byte value : data)
			dataConsumer.accept(value);
	}
	
	@Override
	public byte[] readByteArrayTag() throws IOException {
		ensureOpen();
		if (current.getType() != TagType.BYTE_ARRAY)
			throw new NBTException("Can not read as ByteArrayTag: " + current.getType().name());
		return (byte[]) current.getData();
	}

	@Override
	public String readStringTag() throws IOException {
		ensureOpen();
		if (current.getType() != TagType.STRING)
			throw new NBTException("Can not read as StringTag: " + current.getType().name());
		return (String) current.getData();
	}

	@Override
	public void readNextEntry() throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void readIntArrayTag(IntConsumer dataConsumer) throws IOException {
		ensureOpen();
		if (current.getType() != TagType.INT_ARRAY)
			throw new NBTException("Can not read as IntArrayTag: " + current.getType().name());
		int[] data = (int[]) current.getData();
		for (int value : data)
			dataConsumer.accept(value);
	}

	@Override
	public int[] readIntArrayTag() throws IOException {
		ensureOpen();
		if (current.getType() != TagType.INT_ARRAY)
			throw new NBTException("Can not read as IntArrayTag: " + current.getType().name());
		return (int[]) current.getData();
	}
	
	@Override
	public void readLongArrayTag(LongConsumer dataConsumer) throws IOException {
		ensureOpen();
		if (current.getType() != TagType.LONG_ARRAY)
			throw new NBTException("Can not read as LongArrayTag: " + current.getType().name());
		long[] data = (long[]) current.getData();
		for (long value : data)
			dataConsumer.accept(value);
	}

	@Override
	public long[] readLongArrayTag() throws IOException {
		ensureOpen();
		if (current.getType() != TagType.LONG_ARRAY)
			throw new NBTException("Can not read as LongArrayTag: " + current.getType().name());
		return (long[]) current.getData();
	}

	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public NBT readNBT() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID readUUID() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void skipTag() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mark() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void search(CharSequence key, TagType type, boolean list) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		nbt = null;
		current = null;
		list = null;
	}
	
	protected final void ensureOpen() throws IOException {
		if (closed)
			throw new IOException("Stream closed!");
	}

}
