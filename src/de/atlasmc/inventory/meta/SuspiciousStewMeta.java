package de.atlasmc.inventory.meta;

import java.util.List;

import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;

public interface SuspiciousStewMeta extends ItemMeta {
	
	public void addCustomEffect(PotionEffect effect, boolean overwrite);
	public boolean clearCustomEffects();
	public SuspiciousStewMeta clone();
	public List<PotionEffect>getCustomEffects();
	public boolean hasCustomEffects();
	public boolean hasCustomEffect(PotionEffectType type);
	public boolean removeCustomEffect(PotionEffectType type);
	public boolean removeAllCustomEffects(PotionEffectType type);

}
