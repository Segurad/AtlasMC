package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.DyeColor;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Cat extends Tameable {
	
	public static final NBTSerializationHandler<Cat>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Cat.class)
					.include(Tameable.NBT_HANDLER)
					.enumByteField("CollarColor", Cat::getCollarColor, Cat::setCollarColor, DyeColor::getByID, DyeColor::getID, DyeColor.RED)
					.enumStringField("variant", Cat::getCatType, Cat::setCatType, Type::getByName, Type.BLACK)
					.build();
	
	Type getCatType();
	
	void setCatType(Type type);
	
	boolean isLying();
	
	void setLying(boolean lying);
	
	boolean isRelaxed();
	
	void setRelaxed(boolean relaxed);
	
	DyeColor getCollarColor();
	
	void setCollarColor(DyeColor color);
	
	@Override
	default NBTSerializationHandler<? extends Cat> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Type implements EnumName, EnumID, EnumValueCache {
		TABBY,
		BLACK,
		RED,
		SIAMESE,
		BRITISH_SHORTHAIR,
		CALICO,
		PERSIAN,
		RAGDOLL,
		WHITE,
		JELLIE,
		ALL_BLACK;
		
		private static List<Type> VALUES;
		
		private String name;
		
		private Type() {
			String name = "minecraft:" + name().toLowerCase();
			this.name = name.intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public static Type getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			final List<Type> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Type value = values.get(i);
				if (value.name.equals(name))
					return value;
			}
			return null;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		public static Type getByID(int id) {
			return getValues().get(id);
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
