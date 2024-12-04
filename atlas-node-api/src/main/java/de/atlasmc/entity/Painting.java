package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface Painting extends Hanging {

	public Motive getMotive();
	
	public void setMotive(Motive motive);
	
	public static enum Motive implements EnumID, EnumName, EnumValueCache {
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
		
		private final String name;
		
		private Motive() {
			String name = "minecraft:".concat(name().toLowerCase());
			this.name = name.intern();
		}
		
		@Override
		public String getName() {
			return name;
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
				VALUES = List.of(values());
			return VALUES;
		}
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}

		public static Motive getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<Motive> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				Motive motive = values.get(i);
				if (motive.name.equals(name))
					return motive;
			}
			return null;
		}
		
	}

}
