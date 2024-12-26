package de.atlasmc.block.data.property;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public abstract class AbstractEnumProperty<T extends Enum<?>> extends BlockDataProperty<T> {

	private final Map<String, T> enums;
	
	public AbstractEnumProperty(String key, Class<T> clazz) {
		super(key);
		T[] values = clazz.getEnumConstants();
		Map<String, T> enums = new HashMap<>();
		if (EnumName.class.isAssignableFrom(clazz)) {
			for (T value : values) {
				enums.put(((EnumName) value).getName(), value);
			}
		} else {
			for (T value : values) {
				enums.put(value.name(), value);
			}
		}
		this.enums = Map.copyOf(enums);
	}
	
	public AbstractEnumProperty(String key, Map<String, T> map) {
		super(key);
		this.enums = Map.copyOf(map);
	}

	@Override
	public TagType getType() {
		return TagType.STRING;
	}
	
	@Override
	public String toString(T value) {
		if (value instanceof EnumName named)
			return named.getName();
		return value.name();
	}
	
	@Override
	public T fromString(String value) {
		return enums.get(value);
	}
	
	@Override
	public T fromNBT(NBTReader reader) throws IOException {
		return enums.get(reader.readStringTag());
	}
	
	@Override
	public void toNBT(T value, NBTWriter writer, boolean systemData) throws IOException {
		if (value instanceof EnumName named) {
			writer.writeStringTag(key, named.getName());
		} else {
			writer.writeStringTag(key, value.name());
		}
	}

}
