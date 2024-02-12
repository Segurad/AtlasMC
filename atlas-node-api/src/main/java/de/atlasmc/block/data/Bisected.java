package de.atlasmc.block.data;

import java.util.List;

public interface Bisected extends BlockData {
	
	public Half getHalf();
	
	public void setHalf(Half half);
	
	public static enum Half {
		TOP(0),
		UPPER(0),
		BOTTOM(1),
		LOWER(1);

		private static List<Half> VALUES;
		
		private int id;
		private String nameID;
		
		private Half(int id) {
			this.id = id;
			nameID = name().toLowerCase();
		}
		
		public String getNameID() {
			return nameID;
		}
		
		public static Half getByNameID(String nameID) {
			if (nameID == null)
				throw new IllegalArgumentException("NameID can not be null!");
			for (Half value : getValues()) {
				if (value.getNameID().equals(nameID))
					return value;
			}
			throw new IllegalArgumentException("No value with name found: " + nameID);
		}
		
		public int getID() {
			return id;
		}
		
		public static Half getByID(int id) {
			return getValues().get(id*2);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Half> getValues() {
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
