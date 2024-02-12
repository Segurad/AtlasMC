package de.atlasmc.block.data.type;

import java.util.List;

import de.atlasmc.block.data.Waterlogged;

public interface PointedDripstone extends Waterlogged {
	
	Thickness getThickness();
	
	void setThickness(Thickness thickness);
	
	VerticalDirection getDirection();
	
	void setDirection(VerticalDirection direction);
	
	PointedDripstone clone();
	
	public static enum Thickness {
		
		TIP_MERGE,
		TIP,
		FRUSTUM,
		MIDDLE,
		BASE;

		private static List<Thickness> VALUES;
		
		private final String nameID;
		
		private Thickness() {
			this.nameID = name().toLowerCase();
		}
		
		public String getNameID() {
			return nameID;
		}
		
		public static Thickness getByNameID(String nameID) {
			if (nameID == null)
				throw new IllegalArgumentException("NameID can not be null!");
			for (Thickness value : getValues()) {
				if (value.getNameID().equals(nameID))
					return value;
			}
			throw new IllegalArgumentException("No value found with name: " + nameID);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Thickness> getValues() {
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
	
	public static enum VerticalDirection {
		
		UP,
		DOWN;

		private static List<VerticalDirection> VALUES;
		
		private final String nameID;
		
		private VerticalDirection() {
			this.nameID = name().toLowerCase();
		}
		
		public String getNameID() {
			return nameID;
		}
		
		public static VerticalDirection getByNameID(String nameID) {
			if (nameID == null)
				throw new IllegalArgumentException("NameID can not be null!");
			for (VerticalDirection sound : getValues()) {
				if (sound.getNameID().equals(nameID))
					return sound;
			}
			throw new IllegalArgumentException("No value found with name: " + nameID);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<VerticalDirection> getValues() {
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
