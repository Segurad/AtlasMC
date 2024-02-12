package de.atlasmc.entity;

import java.util.UUID;

import de.atlasmc.entity.Villager.VillagerProfession;
import de.atlasmc.entity.Villager.VillagerType;

public interface ZombieVillager extends Zombie, Merchant {
	
	public boolean isConverting();
	
	public VillagerType getVillagerType();
	
	public VillagerProfession getVillagerProfession();
	
	public int getLevel();
	
	public void setConverting(boolean converting);
	
	public void setVillagerType(VillagerType type);
	
	public void setVillagerProfession(VillagerProfession profession);
	
	public void setLevel(int level);

	public void setXp(int xp);

	public int getXp();

	public void setConversionPlayer(UUID uuid);
	
	public UUID getConversionPlayer();

	public void setConversionTime(int ticks);
	
	/**
	 * Returns the number of ticks it takes until this {@link ZombieVillager} is converted to a {@link Villager}.<br>
	 * Will be -1 if no conversion is happening
	 * @return ticks or -1
	 */
	public int getConversionTime();
	
}
