package de.atlasmc.node.entity;

import java.util.List;

import de.atlasmc.util.AtlasEnum;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Painting extends Hanging {

	public static final NBTSerializationHandler<Painting>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Painting.class)
					.include(Hanging.NBT_HANDLER)
					.enumStringField("motive", Painting::getMotive, Painting::setMotive, Motive::getByName, Motive.KEBAB)
					.build();
	
	Motive getMotive();
	
	void setMotive(Motive motive);
	
	@Override
	default NBTSerializationHandler<? extends Painting> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Motive implements AtlasEnum {
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
