package de.atlasmc.util.nbt.tag;

import java.io.IOException;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class EndTag implements NBT {
	
	public static final EndTag INSTANCE = new EndTag();

	private EndTag() {}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		writer.writeEndTag();
	}

	@Override
	public void fromNBT(NBTReader reader) throws IOException {
		reader.readNextEntry();
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void setName(String name) {
		// holds no name
	}

	@Override
	public Object getData() {
		return null;
	}

	@Override
	public void setData(Object data) {
		// holds no data
	}

	@Override
	public TagType getType() {
		return TagType.TAG_END;
	}
	
	@Override
	public EndTag clone() {
		return INSTANCE;
	}

	@Override
	public void toNBT(CharSequence name, NBTWriter writer, boolean systemData) throws IOException {
		writer.writeEndTag();
	}

}
