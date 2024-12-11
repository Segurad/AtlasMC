package de.atlasmc.chat.component;

import java.io.IOException;

import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class NBTComponent extends AbstractBaseComponent<NBTComponent> {
	
	public static final CharKey
	JSON_SOURCE = CharKey.literal("source"),
	JSON_NBT = CharKey.literal("nbt"),
	JSON_INTERPRET = CharKey.literal("interpret"),
	JSON_SEPARATOR = CharKey.literal("separator"),
	JSON_BLOCK = CharKey.literal("block"),
	JSON_ENTITY = CharKey.literal("entity"),
	JSON_STORAGE = CharKey.literal("storage");
	
	private String source;
	private String nbtPath;
	private boolean interpret;
	private ChatComponent separator;
	private String block;
	private String entity;
	private String storage;
	
	@Override
	public void addContents(NBTWriter writer) throws IOException {
		super.addContents(writer);
		writer.writeStringTag(JSON_NBT, nbtPath);
		if (source != null)
			writer.writeStringTag(JSON_SOURCE, source);
		if (interpret)
			writer.writeByteTag(JSON_INTERPRET, true);
		if (separator != null) {
			writer.writeCompoundTag(JSON_SEPARATOR);
			separator.addContents(writer);
			writer.writeEndTag();
		}
		if (block != null)
			writer.writeStringTag(JSON_BLOCK, block);
		if (entity != null)
			writer.writeStringTag(JSON_ENTITY, entity);
		if (storage != null)
			writer.writeStringTag(JSON_STORAGE, storage);
	}

	@Override
	protected String getType() {
		return "nbt";
	}

	@Override
	protected NBTComponent getThis() {
		return this;
	}

	public String getSource() {
		return source;
	}

	public String getNbtPath() {
		return nbtPath;
	}

	public boolean isInterpret() {
		return interpret;
	}

	public ChatComponent getSeparator() {
		return separator;
	}

	public String getBlock() {
		return block;
	}

	public String getEntity() {
		return entity;
	}

	public String getStorage() {
		return storage;
	}

	public NBTComponent setSource(String source) {
		this.source = source;
		return this;
	}

	public NBTComponent setNbtPath(String nbtPath) {
		this.nbtPath = nbtPath;
		return this;
	}

	public NBTComponent setInterpret(boolean interpret) {
		this.interpret = interpret;
		return this;
	}

	public NBTComponent setSeparator(ChatComponent separator) {
		this.separator = separator;
		return this;
	}

	public NBTComponent setBlock(String block) {
		this.block = block;
		return this;
	}

	public NBTComponent setEntity(String entity) {
		this.entity = entity;
		return this;
	}

	public NBTComponent setStorage(String storage) {
		this.storage = storage;
		return this;
	}

}
