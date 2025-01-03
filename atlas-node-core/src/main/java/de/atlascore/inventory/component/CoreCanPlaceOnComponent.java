package de.atlascore.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.CanPlaceOnComponent;
import de.atlasmc.inventory.component.ComponentType;

public class CoreCanPlaceOnComponent extends CoreAbstractBlockPredicateComponent implements CanPlaceOnComponent {

	public CoreCanPlaceOnComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreCanPlaceOnComponent clone() {
		return (CoreCanPlaceOnComponent) super.clone();
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.CAN_PLACE_ON;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}

}
