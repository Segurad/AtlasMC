package de.atlasmc.inventory.component.effect;

import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.util.dataset.DataSet;

public interface RemoveEffects extends ComponentEffect {

	DataSet<PotionEffectType> getEffects();
	
	void setEffects(DataSet<PotionEffectType> effects);
	
	boolean hasEffects();
	
	RemoveEffects clone();
 	
	@Override
	default ComponentEffectType getType() {
		return ComponentEffectType.REMOVE_EFFECTS;
	}
	
}
