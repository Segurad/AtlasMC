package de.atlasmc.entity;

import de.atlasmc.Location;

public interface Entity {

	public boolean isOnFire();
	public boolean isCrouching();
	public boolean isSprinting();
	public boolean isSwimming();
	public boolean isInvisble();
	public boolean isGlowing();
	public boolean isFlyingWithElytra();
	public int getAirTicks();
	public String getCustomName();
	public boolean isSilent();
	public boolean isCustomNameVisible();
	public boolean hasGravity();
	public Pose getPose();
	
	public void remove();
	public void setCustomNameVisible(boolean value);
	public void setCustomName(String name);
	public Location getLocation();
	
	public enum Pose {
		DYING,
		FALL_FLYING,
		SLEEPING,
		SPIN_ATTACK,
		STANDING,
		SWIMMING,
	}

	public EntityType getType();
	
}
