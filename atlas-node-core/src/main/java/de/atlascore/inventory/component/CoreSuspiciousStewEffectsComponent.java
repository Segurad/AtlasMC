package de.atlascore.inventory.component;

import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.SuspiciousStewEffectsComponent;

public class CoreSuspiciousStewEffectsComponent extends CoreAbstractPotionEffectComponent implements SuspiciousStewEffectsComponent {

	public CoreSuspiciousStewEffectsComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreSuspiciousStewEffectsComponent clone() {
		return (CoreSuspiciousStewEffectsComponent) super.clone();
	}

}
