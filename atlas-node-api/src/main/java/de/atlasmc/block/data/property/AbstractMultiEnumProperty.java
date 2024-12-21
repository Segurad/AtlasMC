package de.atlasmc.block.data.property;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

abstract class AbstractMultiEnumProperty extends AbstractEnumProperty<Enum<?>> {

	private final Map<String, Enum<?>> BY_NAME;
	
	public AbstractMultiEnumProperty(String key, Class<?>...enums) {
		super(key);
		HashMap<String, Enum<?>> map = new HashMap<>();
		for (Class<?> clazz : enums) {
			Enum<?>[] values = (Enum<?>[]) clazz.getEnumConstants();
			for (Enum<?> e : values) {
				if (e instanceof EnumName en) {
					map.put(en.getName(), e);
				} else {
					map.put(e.name(), e);
				}
			}
		}
		BY_NAME = Map.copyOf(map);
	}

	@Override
	public Enum<?> fromNBT(NBTReader reader) throws IOException {
		return BY_NAME.get(reader.readStringTag());
	}

	@Override
	public void toNBT(Enum<?> value, NBTWriter writer, boolean systemData) throws IOException {
		String name = value instanceof EnumName en ? en.getName() : value.name();
		writer.writeStringTag(key, name);
	}

}
