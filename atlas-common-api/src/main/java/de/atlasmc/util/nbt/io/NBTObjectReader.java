package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.CompoundTag;
import de.atlasmc.util.nbt.tag.EndTag;
import de.atlasmc.util.nbt.tag.ListTag;
import de.atlasmc.util.nbt.tag.NBT;

public class NBTObjectReader extends AbstractNBTReader {

	private NBT current;
	private StackElement stack;
	private StackElement markStack;
	private NBT markCurrent;
	private boolean marked;
	private boolean closed;
	private int arrayRestPayload = 0;
	
	public NBTObjectReader(NBT nbt) {
		if (nbt == null)
			throw new IllegalArgumentException("NBT can not be null!");
		this.current = nbt;
	}

	@Override
	public TagType getType() {
		return current.getType();
	}

	@Override
	public TagType getListType() {
		return stack != null ? stack.listType : null;
	}

	@Override
	public int getRestPayload() {
		return stack != null ? stack.getRestPayload() : 0;
	}
	
	@Override
	public int getNextPayload() {
		return stack != null ? stack.getRestPayload() : 0;
	}
	
	@Override
	public String getFieldName() {
		return current != null ? current.getName() : null;
	}

	@Override
	public byte readByteTag() throws IOException {
		ensureOpen();
		if (!current.getType().isNumber())
			throw new NBTException("Can not read as ByteTag: " + current.getType().name());
		byte data = (byte) current.getData();
		prepareTag();
		return data;
	}

	@Override
	public short readShortTag() throws IOException {
		ensureOpen();
		if (!current.getType().isNumber())
			throw new NBTException("Can not read as ShortTag: " + current.getType().name());
		short data = (short) current.getData();
		prepareTag();
		return data;
	}

	@Override
	public int readIntTag() throws IOException {
		ensureOpen();
		if (!current.getType().isNumber())
			throw new NBTException("Can not read as IntTag: " + current.getType().name());
		int data = (int) current.getData();
		prepareTag();
		return data;
	}

	@Override
	public long readLongTag() throws IOException {
		ensureOpen();
		if (!current.getType().isNumber())
			throw new NBTException("Can not read as LongTag: " + current.getType().name());
		long data = (long) current.getData();
		prepareTag();
		return data;
	}

	@Override
	public float readFloatTag() throws IOException {
		ensureOpen();
		if (!current.getType().isNumber())
			throw new NBTException("Can not read as FloatTag: " + current.getType().name());
		float data = (float) current.getData();
		prepareTag();
		return data;
	}

	@Override
	public double readDoubleTag() throws IOException {
		ensureOpen();
		if (!current.getType().isNumber())
			throw new NBTException("Can not read as DoubleTag: " + current.getType().name());
		double data = (double) current.getData();
		prepareTag();
		return data;
	}

	@Override
	public void readByteArrayTag(IntConsumer dataConsumer) throws IOException {
		ensureOpen();
		if (current.getType() != TagType.BYTE_ARRAY)
			throw new NBTException("Can not read as ByteArrayTag: " + current.getType().name());
		byte[] data = (byte[]) current.getData();
		for (byte value : data)
			dataConsumer.accept(value);
		prepareTag();
	}
	
	@Override
	public byte[] readByteArrayTag() throws IOException {
		ensureOpen();
		if (current.getType() != TagType.BYTE_ARRAY)
			throw new NBTException("Can not read as ByteArrayTag: " + current.getType().name());
		byte[] data = (byte[]) current.getData();
		prepareTag();
		return data;
	}

	@Override
	public String readStringTag() throws IOException {
		ensureOpen();
		if (current.getType() != TagType.STRING)
			throw new NBTException("Can not read as StringTag: " + current.getType().name());
		String data = (String) current.getData();
		prepareTag();
		return data;
	}

	@Override
	public void readNextEntry() throws IOException {
		ensureOpen();
			if (current.getType() == TagType.COMPOUND || 
					current.getType() == TagType.TAG_END ||
					current.getType() == TagType.LIST && ((ListTag) current).getDataType() == TagType.COMPOUND)
				prepareTag();
			else
				throw new IOException("Next entry should only be called on COMPOUND or END: " + current.getType().name());
	}
	
	@Override
	public void readIntArrayTag(IntConsumer dataConsumer) throws IOException {
		ensureOpen();
		if (current.getType() != TagType.INT_ARRAY)
			throw new NBTException("Can not read as IntArrayTag: " + current.getType().name());
		int[] data = (int[]) current.getData();
		for (int value : data)
			dataConsumer.accept(value);
		prepareTag();
	}

	@Override
	public int[] readIntArrayTag() throws IOException {
		ensureOpen();
		if (current.getType() != TagType.INT_ARRAY)
			throw new NBTException("Can not read as IntArrayTag: " + current.getType().name());
		int[] data = (int[]) current.getData();
		prepareTag();
		return data;
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
		return stack != null ? stack.depth : -1;
	}

	@Override
	public NBT readNBT() {
		NBT nbt = current;
		prepareTag();
		return nbt;
	}

	@Override
	public void skipTag() throws IOException {
		ensureOpen();
		prepareTag();
	}

	@Override
	public void mark() {
		if (marked || closed)
			return;
		markStack = stack;
		markCurrent = current;
		StackElement element = stack;
		while (element != null) {
			element.mark();
			element = element.parent;
		}
		marked = true;
	}

	@Override
	public void reset() {
		if (!marked || closed)
			return;
		stack = markStack;
		current = markCurrent;
		markStack = null;
		markCurrent = null;
		StackElement element = stack;
		while (element != null) {
			element.reset();
			element = element.parent;
		}
		marked = false;
	}

	@Override
	public void close() {
		if (closed)
			return;
		current = null;
		stack = null;
		markStack = null;
		markCurrent = null;
		closed = true;
	}

	@Override
	public int getArrayTagPayload() {
		return arrayRestPayload;
	}

	@Override
	public int readByteArrayTag(byte[] buf) throws IOException {
		ensureOpen();
		byte[] data = (byte[]) current.getData();
		int length = Math.min(buf.length, arrayRestPayload);
		System.arraycopy(data, data.length-arrayRestPayload, buf, 0, buf.length);
		arrayRestPayload -= length;
		if (arrayRestPayload <= 0)
			prepareTag();
		return length;
	}

	@Override
	public int readIntArrayTag(int[] buf) throws IOException {
		ensureOpen();
		int[] data = (int[]) current.getData();
		int length = Math.min(buf.length, arrayRestPayload);
		System.arraycopy(data, data.length-arrayRestPayload, buf, 0, buf.length);
		arrayRestPayload -= length;
		if (arrayRestPayload <= 0)
			prepareTag();
		return length;
	}

	@Override
	public int readLongArrayTag(long[] buf) throws IOException {
		ensureOpen();
		long[] data = (long[]) current.getData();
		int length = Math.min(buf.length, arrayRestPayload);
		System.arraycopy(data, data.length-arrayRestPayload, buf, 0, buf.length);
		arrayRestPayload -= length;
		if (arrayRestPayload <= 0)
			prepareTag();
		return length;
	}
	
	private void prepareTag() {
		arrayRestPayload = 0;
		if (current == null)
			return;
		if (current.getType() == TagType.TAG_END) {
			current = null;
			if (stack == null)
				return;
			stack = stack.parent;
		} else if (current.getType() == TagType.COMPOUND) {
			stack = new StackElement(stack, (CompoundTag) current);
		} else if (current.getType() == TagType.LIST) {
			ListTag list = (ListTag) current;
			stack = new StackElement(stack, list);
			if (list.getDataType() == TagType.COMPOUND && list.getPayloadSize() > 0) {
				CompoundTag nbt = (CompoundTag) stack.next();
				stack = new StackElement(stack, nbt);
			}
		}
		if (stack == null) {
			return;
		}
		current = null;
		NBT next = stack.next();
		while (next == null && stack.listType != null) {
			stack = stack.parent;
			if (stack == null)
				return;
			next = stack.next();
		};
		current = next == null ? EndTag.INSTANCE : next;
		TagType type = current.getType();
		switch (type) {
		case BYTE_ARRAY:
			arrayRestPayload = ((byte[]) current.getData()).length;
			break;
		case INT_ARRAY:
			arrayRestPayload = ((int[]) current.getData()).length;
			break;
		case LONG_ARRAY:
			arrayRestPayload = ((long[]) current.getData()).length;
			break;
		default:
			break;
		}
	}
	
	private static class StackElement implements Iterator<NBT> {
		
		public final StackElement parent;
		private List<NBT> list;
		public final int size;
		private int index;
		private int mark = -1;
		private final int depth;
		public final TagType listType;
		
		public StackElement(StackElement parent, CompoundTag tag) {
			this(parent, tag.getData(), null);
		}

		public StackElement(StackElement parent, ListTag tag) {
			this(parent, tag.getData(), tag.getDataType());
		}
		
		public StackElement(StackElement parent, List<NBT> list, TagType listType) {
			this.list = list;
			this.size = list.size();
			this.depth = parent == null ? 0 : parent.depth + 1;
			this.parent = parent;
			this.listType = listType;
		}
		
		public int getRestPayload() {
			return size - (index + 1);
		}

		@Override
		public boolean hasNext() {
			for (int i = index+1; i < size; i++) {
				NBT nbt = list.get(i);
				if (nbt != null)
					return true;
			}
			return false;
		}

		@Override
		public NBT next() {
			for (int i = index+1; i < size; i++) {
				NBT nbt = list.get(i);
				if (nbt == null)
					continue;
				index = i;
				return nbt;
			}
			return null;
		}
		
		public void mark() {
			mark = index;
		}
		
		public void reset() {
			index = mark;
			mark = -1;
		}
		
	}

	@Override
	public boolean isList() {
		return current.getType() == TagType.LIST;
	}

}
