package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.inventory.HorseInventory;

public interface Horse extends AbstractHorse {
	
	public HorseColor getColor();
	
	public void setColor(HorseColor color);
	
	public Style getStyle();
	
	public void setStyle(Style style);
	
	public HorseInventory getInventory();
	
	public static enum HorseColor {
		WHITE,
		CREAMY,
		CJESTMIT,
		BROWN,
		BLACK,
		GRAY,
		DARK_BROWN;
		
		private static List<HorseColor> VALUES;

		public int getID() {
			return ordinal();
		}
		
		public static HorseColor getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<HorseColor> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
	}
	
	public static enum Style {
		
		NONE,
		WHITE,
		WHITE_FIELD,
		WHITE_DOTS,
		BLACK_DOTS;
		
		private static List<Style> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static Style getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Style> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
	}

}
