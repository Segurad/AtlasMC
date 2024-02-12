package de.atlasmc.entity;

import java.util.List;

public interface EnderDragon extends Mob {
	
	public DragonPhase getPhase();
	
	public void setPhase(DragonPhase phase);
	
	public static enum DragonPhase {
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

		public int getID() {
			return ordinal();
		}
		
	}

}
