package de.atlasmc.node.block.data.property;

import java.util.HashMap;
import java.util.Map;

import de.atlasmc.util.EnumName;

abstract class AbstractMultiEnumProperty extends AbstractEnumProperty<Enum<?>> {
	
	public AbstractMultiEnumProperty(String key, Class<?>...enums) {
		super(key, build(enums));
	}
	
	private static Map<String, Enum<?>> build(Class<?>[] enums) {
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
		return Map.copyOf(map);
	}

}
