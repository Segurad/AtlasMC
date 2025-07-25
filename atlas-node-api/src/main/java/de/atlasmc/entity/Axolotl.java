package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumValueCache;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Axolotl extends Fish, AgeableMob {
	
	public static final NBTSerializationHandler<Axolotl>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Axolotl.class)
					.include(AgeableMob.NBT_HANDLER)
					.include(Fish.NBT_HANDLER)
					.enumIntField("Variant", Axolotl::getVariant, Axolotl::setVariant, Variant::getByID, Variant.LUCY)
					.build();
	
	Variant getVariant();
	
	void setVariant(Variant variant);

	@Override
	default NBTSerializationHandler<? extends Axolotl> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Variant implements EnumID, EnumValueCache {
		
		LUCY,
		WILD,
		GOLD,
		CYAN,
		BLUE;
		
		private static List<Variant> VALUES;
		
		@Override
		public int getID() {
			return ordinal();
		}
		
		public static Variant getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Variant> getValues() {
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
