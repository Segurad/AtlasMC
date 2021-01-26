package de.atlasmc.entity;

public interface EnderDragon extends Mob {
	
	public DragonPhase getPhase();
	
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
		HOVERING
	}

}
