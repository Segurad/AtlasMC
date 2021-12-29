package de.atlasmc.util.nbt.io;

import de.atlasmc.util.nbt.ListTag;
import de.atlasmc.util.nbt.TagType;

/**
 * Stores Data about a {@link ListTag} and its contents
 */
final class ListData {
	
	public final ListData last;
	public final int depth;
	public int payload;
	public final TagType type;

	public ListData(TagType type, int payload, int depth, ListData last) {
		this.type = type;
		this.depth = depth;
		this.payload = payload;
		this.last = last;
	}
	
}
