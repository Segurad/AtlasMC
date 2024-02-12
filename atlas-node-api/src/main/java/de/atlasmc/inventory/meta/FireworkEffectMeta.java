package de.atlasmc.inventory.meta;

import de.atlasmc.FireworkEffect;

public interface FireworkEffectMeta extends ItemMeta {
	
	public FireworkEffectMeta clone();
	
	public FireworkEffect getEffect();
	
	public boolean hasEffect();
	
	public void setEffect(FireworkEffect effect);

}
