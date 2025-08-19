package de.atlascore.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.AbstractPotionEffectComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.potion.PotionEffect;

public class CoreAbstractPotionEffectComponent extends AbstractItemComponent implements AbstractPotionEffectComponent {

	private List<PotionEffect> effects;
	
	public CoreAbstractPotionEffectComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public List<PotionEffect> getEffects() {
		return effects;
	}

	@Override
	public boolean hasEffects() {
		return effects != null && !effects.isEmpty();
	}
	
	@Override
	public CoreAbstractPotionEffectComponent clone() {
		CoreAbstractPotionEffectComponent clone = (CoreAbstractPotionEffectComponent) super.clone();
		if (hasEffects()) {
			clone.effects = new ArrayList<>(effects.size());
			for (PotionEffect effect : effects) {
				clone.effects.add(effect.clone());
			}
		}
		return clone;
	}

}
