package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.DyeColor;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface Cat extends Tameable {
	
	public Type getCatType();
	
	public void setCatType(Type type);
	
	public boolean isLying();
	
	public void setLying(boolean lying);
	
	public boolean isRelaxed();
	
	public void setRelaxed(boolean relaxed);
	
	public DyeColor getCollarColor();
	
	public void setCollarColor(DyeColor color);
	
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
			List<Type> values = getValues();
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
