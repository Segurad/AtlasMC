package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.util.AtlasEnum;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Frog extends Animal {

	public static final NBTSerializationHandler<Frog>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Frog.class)
					.include(Animal.NBT_HANDLER)
					.enumStringField("variant", Frog::getVariant, Frog::setVariant, Variant::getByName, Variant.TEMPERATE)
					.build();
	
	Variant getVariant();
	
	void setVariant(Variant variant);
	
	Entity getTongueTarget();
	
	void setTangueTarget(Entity entity);
	
	@Override
	default NBTSerializationHandler<? extends Frog> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Variant implements AtlasEnum {
		TEMPERATE,
		WARM,
		COLD;
		
		private static List<Variant> VALUES;
		
		private String name;
		
		private Variant() {
			String name = "minecraft:" + name().toLowerCase();
			this.name = name.intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public static Variant getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Variant> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Variant value = values.get(i);
				if (value.name.equals(name))
					return value;
			}
			return null;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		public static Variant getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Variant> getValues() {
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
