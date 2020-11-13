package de.atlasmc.entity;

public interface Entity {

	public boolean isOnFire();
	public boolean isCrouching();
	public boolean isUnused();
	public boolean isSprinting();
	public boolean isSwimming();
	public boolean isInvisble();
	public boolean isGlowing();
	public boolean isFlyingWithElytra();
	public int getAirTicks();
	public String getCustomName();
	public boolean isSilent();
	public boolean isCustomNameVisable();
	public boolean hasGravity();
	public Pose getPose();
	
	public enum Pose {
		DYING,
		FALL_FLYING,
		SLEEPING,
		SPIN_ATTACK,
		STANDING,
		SWIMMING,
	}
	
}
