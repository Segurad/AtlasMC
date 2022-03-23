package de.atlasmc.inventory.meta;

import java.util.List;

import de.atlasmc.Color;
import de.atlasmc.potion.PotionData;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;

public interface PotionMeta extends ItemMeta {
	
	public default void addCustomEffect(PotionEffect effect) {
		addCustomEffect(effect, false);
	}
	
	public void addCustomEffect(PotionEffect effect, boolean overwrite);
	
	public boolean clearCustomEffects();
	
	public PotionMeta clone();
	
	public PotionData getBaseData();
	
	public Color getColor();
	
	public List<PotionEffect>getCustomEffects();
	
	public boolean hasColor();
	
	public boolean hasCustomEffect(PotionEffectType type);
	
	public boolean hasCustomEffects();
	
	public boolean removeCustomEffect(PotionEffectType type);
	
	public boolean removeAllCustomEffects(PotionEffectType type);
	
	public void setBasePotionData(PotionData data);
	
	public boolean hasBasePotionData();
	
	public void setColor(Color color);
	
	public int getCustomEffectCount();
	
}
