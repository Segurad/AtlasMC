package de.atlasmc.core.node.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.AbstractPotionEffectComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.potion.PotionEffect;

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
