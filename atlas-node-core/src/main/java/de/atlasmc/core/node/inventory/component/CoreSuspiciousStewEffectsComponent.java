package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.SuspiciousStewEffectsComponent;

public class CoreSuspiciousStewEffectsComponent extends CoreAbstractPotionEffectComponent implements SuspiciousStewEffectsComponent {

	public CoreSuspiciousStewEffectsComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreSuspiciousStewEffectsComponent clone() {
		return (CoreSuspiciousStewEffectsComponent) super.clone();
	}

}
