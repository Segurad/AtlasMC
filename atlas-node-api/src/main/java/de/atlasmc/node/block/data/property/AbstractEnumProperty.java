package de.atlasmc.node.block.data.property;

import java.io.IOException;
import java.util.Map;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.enums.EnumUtil;
import de.atlasmc.util.enums.EnumUtil.EnumData;

public abstract class AbstractEnumProperty<T extends Enum<?>> extends PropertyType<T> {

	private final Map<String, T> enums;
	
	public AbstractEnumProperty(String key, Class<T> clazz) {
		super(key);
		EnumData<T> data = EnumUtil.getData(clazz);
		this.enums = data.getByName();
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
