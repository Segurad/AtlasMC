package de.atlasmc.nbt.io;

import java.io.IOException;

import de.atlasmc.nbt.TagType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.map.key.CharKeyBuffer;

public abstract class AbstractNBTStreamReader extends AbstractNBTReader {
	
	protected TagType type;
	private TagType markType;
	protected CharKeyBuffer name;
	protected boolean prepared;
	private boolean markPrepared;
	protected boolean hasName;
	private String markName;
	protected int depth = -1;
	private int markDepth;
	protected ListData list;
	private ListData markList;
	protected int arrayTagPayload = -1;
	private int markArrayTagPayload = -1;
	
	public AbstractNBTStreamReader() {
		this.name = new CharKeyBuffer();
	}
	
	@Override
	public int getDepth() throws IOException {
		if (depth < 0)
			return depth;
		prepareTag();
		return depth;
	}
	
	@Override
	public CharKey getFieldName() throws IOException {
		prepareTag();
		return hasName ? name.getView() : null;
	}
	
	@Override
	public TagType getListType() throws IOException {
		prepareTag();
		return list == null ? null : list.type;
	}
	
	@Override
	public int getRestPayload() throws IOException {
		prepareTag();
		if (list == null)
			return -1;
		if (list.depth != depth) {
			if (type == TagType.LIST && list.depth == depth + 1) { // required for list in list reading
				ListData parent = list.parent;
				if (parent != null) {
					return parent.payload;
				}
			}
			return -1;
		}
		return list.payload;
	}
	
	@Override
	public int getNextPayload() throws IOException {
		prepareTag();
		if (list == null || list.depth >= depth)
			return -1;
		return list.depth;
	}

	@Override
	public TagType getType() throws IOException {
		prepareTag();
		return type;
	}
	
	protected void resetName() {
		name.clear();
		hasName = false;
	}
	
	protected void prepareTag() throws IOException {
		prepareTag(false);
	}
	
	protected abstract void prepareTag(boolean skip) throws IOException;
	
	/**
	 * Call to mark the current tag as consumed. In most cases this can be called
	 * immediately.
	 */
	protected void tagConsumed() {
		prepared = false;
		resetName();
		arrayTagPayload = -1;
		if (list == null)
			return;
		if (depth != list.depth)
			return;
		if (list.payload > 0) {
			list.payload--;
		}
		if (list.payload == 0) {
			prepared = true;
			type = TagType.LIST;
		}
	}
	
	@Override
	public void readNextEntry() throws IOException {
		readNextEntry(false);
	}
	
	protected void readNextEntry(boolean skip) throws IOException {
		prepareTag(skip);
		if (type == TagType.LIST) {
			boolean entered = list.entered;
			if (!entered) { // see if enter
				depth++;
				list.entered = true;
				prepared = false;
				arrayTagPayload = -1;
				resetName();
				return;
			} else if(getRestPayload() == 0) {
				removeList();
				tagConsumed();
				return;
			}
		} else if (type == TagType.TAG_END) {
			depth--;
			tagConsumed();
			return;
		} else if (type == TagType.COMPOUND) {
			depth++;
			tagConsumed();
			return;
		}
		throw new IOException("Next entry should only be called on LIST, COMPOUND or END: " + type.name());
	}
	
	protected void addList(TagType type, int payload) {
		list = new ListData(type, payload, depth + 1, list);
	}
	
	protected void removeList() {
		if (list == null)
			return;
		list = list.parent;
		depth--;
	}
	
	@Override
	public void skipTag() throws IOException {
		skipTag(false);
	}
	
	protected abstract void skipTag(boolean skip) throws IOException;
	
	@Override
	public void skipToEnd() throws IOException {
		final int depth = getDepth();
		while (depth <= getDepth()) {
			if (getType() == TagType.TAG_END && depth == getDepth())
				readNextEntry();
			else
				skipTag(true);
		}
	}
	
	@Override
	public boolean isList() {
		return list != null && depth == list.depth;
	}

	@Override
	public void mark() {
		markDepth = depth;
		if (list != null) {
			markList = list;
			list.mark();
		}
		markName = name.toString();
		markType = type;
		markArrayTagPayload = arrayTagPayload;
		markPrepared = prepared;
	}

	@Override
	public void reset() {
		depth = markDepth;
		list = markList;
		markList = null;
		if (list != null)
			list.reset();
		name.clear();
		name.append(markName);
		markName = null;
		type = markType;
		arrayTagPayload = markArrayTagPayload;
		markArrayTagPayload = -1;
		prepared = markPrepared;
	}

	@Override
	public int getArrayTagPayload() {
		return arrayTagPayload;
	}

	@Override
	public void close() {
		super.close();
		type = null;
		markType = null;
		name = null;
		hasName = false;
		markName = null;
		depth = Integer.MIN_VALUE;
		list = null;
		markList = null;
	}

}
