package de.atlasmc.inventory.meta;

import java.util.List;

import de.atlasmc.Color;
import de.atlasmc.potion.PotionData;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;

public interface PotionMeta extends ItemMeta {
	
	public boolean addCustomEffect(PotionEffect effect, boolean overwrite);
	public boolean clearCustomEffects();
	public PotionMeta clone();
	public PotionData getBaseData();
	public Color getColor();
	public List<PotionEffect>getCustomEffects();
	public boolean hasColor();
	public boolean hasCustomEffect(PotionEffectType type);
	public boolean hasCustomEffects();
	public boolean removeCustomEffect(PotionEffectType type);
	public void setBasePotionData(PotionData data);
	public void setColor(Color color);
	public boolean setMainEffect(PotionEffectType type);
	
}
