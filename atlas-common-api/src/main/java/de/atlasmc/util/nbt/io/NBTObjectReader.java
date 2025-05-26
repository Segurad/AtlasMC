package de.atlasmc.util.nbt.io;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.CompoundTag;
import de.atlasmc.util.nbt.tag.EndTag;
import de.atlasmc.util.nbt.tag.ListTag;
import de.atlasmc.util.nbt.tag.NBT;
import de.atlasmc.util.nbt.tag.NumberTag;

public class NBTObjectReader extends AbstractNBTReader {

	private NBT current;
	private StackElement stack;
	private StackElement markStack;
	private NBT markCurrent;
	protected boolean prepared;
	private boolean markPrepared;
	private boolean marked;
	private boolean closed;
	private int arrayRestPayload = -1;
	private int markArrayTagPayload = -1;
	private int depth = -1;
	private int markDepth;
	
	public NBTObjectReader(NBT nbt) {
		if (nbt == null)
			throw new IllegalArgumentException("NBT can not be null!");
		this.current = nbt;
	}

	@Override
	public void readByteArrayTag(IntConsumer dataConsumer) throws IOException {
		prepareTag();
		ensureTag(TagType.BYTE_ARRAY);
		if (dataConsumer == null)
			throw new IllegalArgumentException("DataConsumer can not be null!");
		byte[] data = (byte[]) current.getData();
		for (byte value : data)
			dataConsumer.accept(value);
		tagConsumed();
	}
	
	@Override
	public int readByteArrayTag(byte[] buf) throws IOException {
		prepareTag();
		ensureTag(TagType.BYTE_ARRAY);
		byte[] data = (byte[]) current.getData();
		int length = Math.min(buf.length, arrayRestPayload);
		System.arraycopy(data, data.length-arrayRestPayload, buf, 0, buf.length);
		arrayRestPayload -= length;
		if (arrayRestPayload <= 0)
			tagConsumed();
		return length;
	}
	
	@Override
	public byte[] readByteArrayTag() throws IOException {
		prepareTag();
		ensureTag(TagType.BYTE_ARRAY);
		byte[] data = (byte[]) current.getData();
		tagConsumed();
		return data;
	}
	
	@Override
	public byte readByteTag() throws IOException {
		prepareTag();
		final TagType type = getType();
		ensureNumberTag(type);
		NumberTag number = (NumberTag) current;
		byte data = number.asByte();
		tagConsumed();
		return data;
	}
	
	@Override
	public double readDoubleTag() throws IOException {
		prepareTag();
		final TagType type = getType();
		ensureNumberTag(type);
		NumberTag number = (NumberTag) current;
		double data = number.asDouble();
		tagConsumed();
		return data;
	}
	
	@Override
	public float readFloatTag() throws IOException {
		prepareTag();
		final TagType type = getType();
		ensureNumberTag(type);
		NumberTag number = (NumberTag) current;
		float data = number.asFloat();
		tagConsumed();
		return data;
	}
	
	@Override
	public void readIntArrayTag(IntConsumer dataConsumer) throws IOException {
		prepareTag();
		ensureTag(TagType.INT_ARRAY);
		if (dataConsumer == null)
			throw new IllegalArgumentException("DataConsumer can not be null!");
		int[] data = (int[]) current.getData();
		for (int value : data)
			dataConsumer.accept(value);
		tagConsumed();
	}

	@Override
	public int readIntArrayTag(int[] buf) throws IOException {
		prepareTag();
		ensureTag(TagType.INT_ARRAY);
		int[] data = (int[]) current.getData();
		int length = Math.min(buf.length, arrayRestPayload);
		System.arraycopy(data, data.length-arrayRestPayload, buf, 0, buf.length);
		arrayRestPayload -= length;
		if (arrayRestPayload <= 0)
			tagConsumed();
		return length;
	}
	
	@Override
	public int[] readIntArrayTag() throws IOException {
		prepareTag();
		ensureTag(TagType.INT_ARRAY);
		int[] data = (int[]) current.getData();
		tagConsumed();
		return data;
	}
	
	@Override
	public int readIntTag() throws IOException {
		prepareTag();
		final TagType type = getType();
		ensureNumberTag(type);
		NumberTag number = (NumberTag) current;
		int data = number.asInteger();
		tagConsumed();
		return data;
	}
	
	@Override
	public void readLongArrayTag(LongConsumer dataConsumer) throws IOException {
		prepareTag();
		ensureTag(TagType.LONG_ARRAY);
		if (dataConsumer == null)
			throw new IllegalArgumentException("DataConsumer can not be null!");
		long[] data = (long[]) current.getData();
		for (long value : data)
			dataConsumer.accept(value);
		tagConsumed();
	}

	@Override
	public int readLongArrayTag(long[] buf) throws IOException {
		prepareTag();
		ensureTag(TagType.LONG_ARRAY);
		long[] data = (long[]) current.getData();
		int length = Math.min(buf.length, arrayRestPayload);
		System.arraycopy(data, data.length-arrayRestPayload, buf, 0, buf.length);
		arrayRestPayload -= length;
		if (arrayRestPayload <= 0)
			tagConsumed();
		return length;
	}
	
	@Override
	public long[] readLongArrayTag() throws IOException {
		prepareTag();
		ensureTag(TagType.LONG_ARRAY);
		long[] data = (long[]) current.getData();
		tagConsumed();
		return data;
	}
	
	@Override
	public long readLongTag() throws IOException {
		prepareTag();
		final TagType type = getType();
		ensureNumberTag(type);
		NumberTag number = (NumberTag) current;
		long data = number.asLong();
		tagConsumed();
		return data;
	}
	
	@Override
	public short readShortTag() throws IOException {
		prepareTag();
		final TagType type = getType();
		ensureNumberTag(type);
		NumberTag number = (NumberTag) current;
		short data = number.asShort();
		tagConsumed();
		return data;
	}
	
	@Override
	public String readStringTag() throws IOException {
		prepareTag();
		ensureTag(TagType.STRING);
		String data = (String) current.getData();
		tagConsumed();
		return data;
	}

	private void prepareTag() throws IOException {
		ensureOpen();
		if (prepared)
			return;
		prepared = true;
		if (stack == null) {
			return;
		}
		NBT next = stack.next();
		current = next == null ? EndTag.INSTANCE : next;
		TagType type = current.getType();
		switch (type) {
		case LIST:
			addList();
			break;
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
	
	private void addList() {
		stack = new StackElement(stack, (ListTag) current, depth + 1);
	}
	
	/**
	 * Call to mark the current tag as consumed. In most cases this can be called
	 * immediately.
	 * @throws IOException 
	 */
	protected void tagConsumed() throws IOException {
		prepared = false;
		arrayRestPayload = -1;
		if (stack != null)
			stack.consumed++;
		if (isList() && getRestPayload() == 0) {
			prepared = true;
			current = stack.tag;
		}
//		if (list == null)
//			return;
//		if (depth != list.depth)
//			return;
//		if (list.payload > 0) {
//			list.payload--;
//		}
//		if (list.payload == 0) {
//			prepared = true;
//			type = TagType.LIST;
//		}
	}
	
	// ----------------------
	
	@Override
	public TagType getType() throws IOException {
		prepareTag();
		return current.getType();
	}

	@Override
	public TagType getListType() throws IOException {
		prepareTag();
		if (current instanceof ListTag list)
			return list.getDataType();
		return stack != null ? stack.listType : null;
	}

	@Override
	public int getRestPayload() throws IOException {
		prepareTag();
		return stack != null ? stack.getRestPayload() : 0;
	}
	
	@Override
	public int getNextPayload() throws IOException {
		prepareTag();
		int payload = 0;
		if (current instanceof ListTag list) {
			payload = list.getPayloadSize();
		}
		return payload;
	}
	
	@Override
	public String getFieldName() throws IOException {
		prepareTag();
		return current != null ? current.getName() : null;
	}

	@Override
	public void readNextEntry() throws IOException {
		prepareTag();
		final TagType type = getType();
		if (type == TagType.LIST) {
			boolean entered = stack.entered;
			if (!entered) { // see if enter
				depth ++;
				stack.entered = true;
				prepared = false;
				arrayRestPayload = -1;
				return;
			} else if(getRestPayload() == 0) {
				stack = stack.parent;
				depth--;
				current = null;
				tagConsumed();
				return;
			}
		} else if (type == TagType.TAG_END) {
			current = null;
			if (stack == null)
				return;
			stack = stack.parent;
			depth--;
			tagConsumed();
			return;
		} else if (type == TagType.COMPOUND) {
			stack = new StackElement(stack, (CompoundTag) current, ++depth);
			tagConsumed();
			return;
		}
		throw new IOException("Next entry should only be called on LIST, COMPOUND or END: " + type.name());
	}

	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public NBT readNBT() throws IOException {
		prepareTag();
		NBT nbt = current;
		tagConsumed();
		return nbt;
	}

	@Override
	public void skipTag() throws IOException {
		prepareTag();
		tagConsumed();
	}

	@Override
	public void mark() {
		markArrayTagPayload = arrayRestPayload;
		markStack = stack;
		markCurrent = current;
		markPrepared = prepared;
		markDepth = depth;
		
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
		depth = markDepth;
		markDepth = -1;
		stack = markStack;
		current = markCurrent;
		prepared = markPrepared;
		arrayRestPayload = markArrayTagPayload;
		markArrayTagPayload = -1;
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
	
	private static class StackElement implements Iterator<NBT> {
		
		public final StackElement parent;
		private List<NBT> list;
		public final int size;
		private int index;
		private int consumed;
		private int mark = -1;
		private final int depth;
		public final TagType listType;
		public final NBT tag;
		public boolean entered;
		private boolean markEntered;
		
		public StackElement(StackElement parent, CompoundTag tag, int depth) {
			this(tag, parent, tag.getData(), null, depth);
			entered = true;
		}

		public StackElement(StackElement parent, ListTag tag, int depth) {
			this(tag, parent, tag.getData(), tag.getDataType(), depth);
		}
		
		public StackElement(NBT tag, StackElement parent, List<NBT> list, TagType listType, int depth) {
			this.list = list;
			this.size = list.size();
			this.depth = depth;
			this.parent = parent;
			this.listType = listType;
			this.tag = tag;
		}
		
		public int getRestPayload() {
			return size - consumed;
		}

		@Override
		public boolean hasNext() {
			for (int i = index; i < size; i++) {
				NBT nbt = list.get(i);
				if (nbt != null)
					return true;
			}
			return false;
		}

		@Override
		public NBT next() {
			for (int i = index; i < size; i++) {
				NBT nbt = list.get(i);
				if (nbt == null)
					continue;
				index = i + 1;
				return nbt;
			}
			return null;
		}
		
		public void mark() {
			mark = index;
			markEntered = entered;
		}
		
		public void reset() {
			index = mark;
			mark = -1;
			entered = markEntered;
			markEntered = false;
		}
		
	}

	@Override
	public boolean isList() {
		return stack != null && stack.listType != null && depth == stack.depth;
	}

}
