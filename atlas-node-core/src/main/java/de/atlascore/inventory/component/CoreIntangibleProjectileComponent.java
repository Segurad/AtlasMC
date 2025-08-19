package de.atlascore.inventory.component;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.IntangibleProjectileComponent;

public class CoreIntangibleProjectileComponent extends AbstractItemComponent implements IntangibleProjectileComponent {

	public CoreIntangibleProjectileComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreIntangibleProjectileComponent clone() {
		return (CoreIntangibleProjectileComponent) super.clone();
	}
	
}
