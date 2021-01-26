package de.atlasmc.entity;

public interface Panda extends Animal {
	
	public int getBreedTimer();
	public int getSneezeTimer();
	public int getEatTimer();
	public Gene getMainGene();
	public Gene getHiddenGene();
	public boolean isSneezing();
	public boolean isRolling();
	public boolean isSitting();
	public boolean isOnBack();
	
	public static enum Gene {
		
	}

}
