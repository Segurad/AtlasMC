package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Ominous;
import de.atlasmc.util.enums.EnumName;

public interface TrialSpawner extends Ominous {
	
	TrialSpawnerState getState();
	
	void setState(TrialSpawnerState state);
	
	public static enum TrialSpawnerState implements EnumName {
		
		INACTIVE,
		WAITING_FOR_PLAYERS,
		ACTIVE,
		WAITING_FOR_REWARD_EJECTION,
		EJECTING_REWARD,
		COOLDOWN;
		
		private final String name;
		
		private TrialSpawnerState() {
			this.name = name().toLowerCase().intern();
		}
		
		@Override
		public String getName() {
			return name;
		}

	}
	
}
