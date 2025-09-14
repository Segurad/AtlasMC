package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.IntangibleProjectileComponent;

public class CoreIntangibleProjectileComponent extends AbstractItemComponent implements IntangibleProjectileComponent {

	public CoreIntangibleProjectileComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreIntangibleProjectileComponent clone() {
		return (CoreIntangibleProjectileComponent) super.clone();
	}
	
}
