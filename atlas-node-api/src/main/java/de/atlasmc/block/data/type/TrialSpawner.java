package de.atlasmc.block.data.type;

import java.util.List;

import de.atlasmc.block.data.Ominous;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumValueCache;

public interface TrialSpawner extends Ominous {
	
	TrialSpawnerState getState();
	
	void setState(TrialSpawnerState state);
	
	public static enum TrialSpawnerState implements EnumName, EnumValueCache {
		
		ACTIVE,
		COOLDOWN,
		EJECTING_REWARD,
		INACTIVE,
		WAITING_FOR_PLAYERS,
		WAITING_FOR_REWARD_EJECTION;
		
		private static List<TrialSpawnerState> VALUES;
		
		private final String name;
		
		private TrialSpawnerState() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public static TrialSpawnerState getByName(String name) {
			if (name == null)
				throw new IllegalArgumentException("Name can not be null!");
			List<TrialSpawnerState> values = getValues();
			final int size = values.size();
			for (int i = 0; i < size; i++) {
				TrialSpawnerState value = values.get(i);
				if (value.name.equals(name))
					return value;
			}
			return null;
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<TrialSpawnerState> getValues() {
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
