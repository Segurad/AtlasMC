package de.atlasmc.entity;

import java.util.List;

import de.atlasmc.Color;
import de.atlasmc.potion.PotionData;
import de.atlasmc.potion.PotionEffect;

public interface Arrow extends AbstractArrow {

	public Color getColor();
	
	public void setColor(Color color);
	
	public int getColorRGB();
	
	public void setColor(int rgb);

	public void addPotionEffect(PotionEffect effect);
	
	public List<PotionEffect> getPotionEffects();
	
	public boolean hasPotionEffects();
	
	public void removePotionEffect(PotionEffect effect);

	public void setPotionData(PotionData data);
	
	public PotionData getPotionData();
	
}
