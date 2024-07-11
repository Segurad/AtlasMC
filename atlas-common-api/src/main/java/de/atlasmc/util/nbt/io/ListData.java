package de.atlasmc.util.nbt.io;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.tag.ListTag;

/**
 * Stores Data about a {@link ListTag} and its contents
 */
final class ListData {
	
	/**
	 * The parent of this list
	 */
	public final ListData parent;
	/**
	 * The depth this list is at
	 */
	public final int depth;
	/**
	 * Current rest payload of this list 
	 */
	public int payload;
	/**
	 * Rest payload of this list mirrored on mark
	 */
	public int markPayload;
	/**
	 * {@link TagType} of this list
	 */
	public final TagType type;
	
	/**
	 * Whether or not this list was entered with {@link NBTReader#readNextEntry()}
	 */
	public boolean entered = false;
	
	public boolean markEntered;

	public ListData(TagType type, int payload, int depth, ListData parent) {
		this.type = type;
		this.depth = depth;
		this.payload = payload;
		this.parent = parent;
	}
	
	public void mark() {
		markEntered = entered;
		markPayload = payload;
		if (parent != null)
			parent.mark();
	}
	
	public void reset() {
		entered = markEntered;
		payload = markPayload;
		if (parent != null)
			parent.reset();
	}
	
}
