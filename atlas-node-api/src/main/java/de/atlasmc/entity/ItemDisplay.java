package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.inventory.ItemStack;

public interface ItemDisplay extends Display {
	
	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	RenderType getRenderType();
	
	void setRenderType(RenderType renderType);
	
	public static enum RenderType {
		
		NONE,
		THIRDPERSON_LEFT_HAND,
		THIRDPERSON_RIGHT_HAND,
		FIRSTPERSON_LEFT_HAND,
		FIRSTPERSON_RIGHT_HAND,
		HEAD,
		GUI,
		GROUND,
		FIXED;
		
		private static List<RenderType> VALUES;
		
		private String nameID;
		
		private RenderType() {
			nameID = name().toLowerCase();
		}
		
		public String getNameID() {
			return nameID;
		}
		
		public static RenderType getByNameID(String nameID) {
			if (nameID == null)
				throw new IllegalArgumentException("NameID can not be null!");
			for (RenderType value : getValues()) {
				if (value.getNameID().equals(nameID))
					return value;
			}
			throw new IllegalArgumentException("No value with name found: " + nameID);
		}
		
		public int getID() {
			return ordinal();
		}
		
		public static RenderType getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<RenderType> getValues() {
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
