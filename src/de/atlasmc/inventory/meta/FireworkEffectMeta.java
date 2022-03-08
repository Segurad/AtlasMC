package de.atlasmc.inventory.meta;

import de.atlasmc.FireworkEffect;

public interface FireworkEffectMeta extends ItemMeta {
	
	public FireworkEffectMeta clone();
	
	@Override
	public default Class<? extends FireworkEffectMeta> getInterfaceClass() {
		return FireworkEffectMeta.class;
	}
	
	public FireworkEffect getEffect();
	
	public boolean hasEffect();
	
	public void setEffect(FireworkEffect effect);

}
