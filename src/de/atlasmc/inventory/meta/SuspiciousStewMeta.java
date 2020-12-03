package de.atlasmc.inventory.meta;

import java.util.List;

import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;

public interface SuspiciousStewMeta extends ItemMeta {
	
	public boolean addCustomEffect(PotionEffect effect, boolean overwrite);
	public boolean clearCustomEffects();
	public SuspiciousStewMeta clone();
	public List<PotionEffect>getCustomEffects();
	public boolean hasCustomEffects();
	public boolean removeCustomEffect(PotionEffectType type);

}
