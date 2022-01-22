package de.atlasmc.entity;

public interface Trident extends AbstractArrow {
	
	public int getLoyalityLevel();
	
	public void setLoyalityLevel(int level);
	
	public boolean hasEnchantmentGlint();

	public void setEnchantmentGlint(boolean glint);
	
}
