package de.atlasmc.entity;

import java.util.UUID;

import de.atlasmc.Location;

public interface Bee extends Animal {

	public boolean isAngry();
	
	public void setAngry(boolean angry);
	
	public boolean hasStung();
	
	public void setStung(boolean stung);
	
	public boolean hasNectar();
	
	public void setNectar(boolean nectar);
	
	public int getAnger();

	public void setAnger(int ticks);
	
	public int getTicksInHive();
	
	public int getHiveMinOccupationTicks();
	
	public void setTicksInHive(int ticks);
	
	public void setHiveMinOccupationTicks(int ticks);

	public void setHiveLocation(Location location);
	
	public Location getHiveLocation();
	
	public void setFlowerLocation(Location location);
	
	public Location getFlowerLocation();

	public void setTicksSincePollination(int ticks);
	
	public int getTicksSincePollination();
	
	public void setTicksCannotEnterHive(int ticks);
	
	public int getTicksCannotEnterHive();
	
	public void setCropsGrownSincePollination(int crops);
	
	public int getCropsGrownSincePollination();

	public void setHurtBy(UUID uuid);
	
	public UUID getHurtBy();
	
}
