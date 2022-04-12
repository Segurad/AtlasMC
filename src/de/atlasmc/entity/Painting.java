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
		SKULL_AND_ROSE,
		WITHER,
		FIGHTERS,
		POINTER,
		PIGSCENE,
		BURNING_SKULL,
		SKELETON,
		DONEKY_KONG;

		private static List<Motive> VALUES;
		
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
		
	}

}
