package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.DyeColor;

public interface Cat extends Tameable {
	
	public Type getCatType();
	
	public void setCatType(Type type);
	
	public boolean isLying();
	
	public void setLying(boolean lying);
	
	public boolean isRelaxed();
	
	public void setRelaxed(boolean relaxed);
	
	public DyeColor getCollarColor();
	
	public void setCollarColor(DyeColor color);
	
	public static enum Type {
		TABBY,
		BLACK,
		RED,
		SIAMESE,
		BRITISH_SHORTHAIR,
		CALICO,
		PERSIAN,
		RAGDOLL,
		WHITE,
		ALL_BLACK;
		
		private static List<Type> VALUES;
		
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
