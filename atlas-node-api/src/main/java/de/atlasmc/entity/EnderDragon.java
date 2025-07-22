package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumValueCache;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface EnderDragon extends Mob {
	
	public static final NBTSerializationHandler<EnderDragon>
	NBT_HANDLER = NBTSerializationHandler
					.builder(EnderDragon.class)
					.include(Mob.NBT_HANDLER)
					.enumIntField("DragonPhase", EnderDragon::getPhase, EnderDragon::setPhase, DragonPhase::getByID, DragonPhase.HOVERING)
					.build();
	
	DragonPhase getPhase();
	
	void setPhase(DragonPhase phase);
	
	@Override
	default NBTSerializationHandler<? extends EnderDragon> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum DragonPhase implements EnumID, EnumValueCache {
		CIRCLING,
		STRAFING,
		FLYING_TO_PORTAL,
		LANDING,
		TAKING_OFF,
		LANDED_BREATH_ATTACK,
		LANDED_BREATH_ATTACK_LOOKING,
		LANDED_BREATH_ATTACK_BEGINNING,
		CHARGING,
		FLYING_TO_DIE,
		HOVERING;
		
		private static List<DragonPhase> VALUES;
		
		public static DragonPhase getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<DragonPhase> getValues() {
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

		@Override
		public int getID() {
			return ordinal();
		}
		
	}

}
