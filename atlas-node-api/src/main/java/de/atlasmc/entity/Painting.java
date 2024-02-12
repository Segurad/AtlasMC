package de.atlasmc.entity;

import java.util.List;

public interface Painting extends Hanging {

	public Motive getMotive();
	
	public void setMotive(Motive motive);
	
	public static enum Motive {
		KEBAB,
		AZTEC,
		ALBAN,
		AZTEC2,
		BOMB,
		PLANT,
		WASTELAND,
		POOL,
		COURBET,
		SEA,
		SUNSET,
		CREEBET,
		WANDERER,
		GRAHAM,
		MATCH,
		BUST,
		STAGE,
		VOID,
		SKULL_AND_ROSES,
		WITHER,
		FIGHTERS,
		POINTER,
		PIGSCENE,
		BURNING_SKULL,
		SKELETON,
		EARTH,
		WIND,
		WATER,
		FIRE,
		DONKEY_KONG;

		private static List<Motive> VALUES;
		
		private final String nameID;
		
		private Motive() {
			this.nameID = "minecraft:".concat(name().toLowerCase());
		}
		
		public String getNameID() {
			return nameID;
		}
		
		public static Motive getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Motive> getValues() {
			if (VALUES == null)
				synchronized (Motive.class) {
					if (VALUES == null)
						VALUES = List.of(values());
				}
			return VALUES;
		}
		
		public int getID() {
			return ordinal();
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}

		public static Motive getByNameID(String nameID) {
			for (Motive motive : getValues()) {
				if (motive.getNameID().equals(nameID))
					return motive;
			}
			return null;
		}
		
	}

}
