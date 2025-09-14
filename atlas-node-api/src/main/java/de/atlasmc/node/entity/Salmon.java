package de.atlasmc.node.entity;

import java.util.List;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Salmon extends Fish {
	
	public static final NBTSerializationHandler<Salmon>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Salmon.class)
					.include(Fish.NBT_HANDLER)
					.enumStringField("type", Salmon::getSalmonType, Salmon::setSalmonType, Type::getByName, Type.MEDIUM)
					.build();

	Type getSalmonType();
	
	void setSalmonType(Type type);
	
	@Override
	default NBTSerializationHandler<? extends Fish> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Type implements EnumName, EnumValueCache {
		SMALL,
		MEDIUM,
		LARGE;
		
		private static List<Type> VALUES;
		
		private final String name;
		
		private Type() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public static Type getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Type> types = getValues();
			final int size = types.size();
			for (int i = 0; i < size; i++) {
				Type type = types.get(i);
				if (type.name.equals(name))
					return type;
			}
			return null;
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Type> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}
	
}
