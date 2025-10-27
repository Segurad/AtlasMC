package de.atlasmc.core.node.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.DeathProtectionComponent;
import de.atlasmc.node.inventory.component.effect.ComponentEffect;

public class CoreDeathProtectionComponent extends AbstractItemComponent implements DeathProtectionComponent {
	
	private List<ComponentEffect> effects;
	
	public CoreDeathProtectionComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreDeathProtectionComponent clone() {
		CoreDeathProtectionComponent clone = (CoreDeathProtectionComponent) super.clone();
		if (effects != null) {
			clone.effects = new ArrayList<>();
			final int size = effects.size();
			for (int i = 0; i < size; i++) {
				ComponentEffect effect = effects.get(i);
				clone.effects.add(effect.clone());
			}
		}
		return clone;
	}

	@Override
	public List<ComponentEffect> getEffects() {
		if (effects == null)
			effects = new ArrayList<>();
		return effects;
	}

	@Override
	public boolean hasEffects() {
		return effects != null && !effects.isEmpty();
	}

	@Override
	public void addEffect(ComponentEffect effect) {
		getEffects().add(effect);
	}

	@Override
	public void removeEffect(ComponentEffect effect) {
		if (effects == null)
			return;
		effects.remove(effect);
	}

}
