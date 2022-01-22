package de.atlasmc.entity;

import de.atlasmc.entity.Villager.VillagerProfession;
import de.atlasmc.entity.Villager.VillagerType;

public interface ZombieVillager extends Zombie {
	
	public boolean isConverting();
	
	public VillagerType getVillagerType();
	
	public VillagerProfession getVillagerProfession();
	
	public int getLevel();
	
	public void setConverting(boolean converting);
	
	public void setVillagerType(VillagerType type);
	
	public void setVillagerProfession(VillagerProfession profession);
	
	public void setLevel(int level);

}
