package de.atlascore.inventory.component;

import de.atlasmc.inventory.component.CanPlaceOnComponent;
import de.atlasmc.inventory.component.ComponentType;

public class CoreCanPlaceOnComponent extends CoreAbstractBlockPredicateComponent implements CanPlaceOnComponent {

	public CoreCanPlaceOnComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreCanPlaceOnComponent clone() {
		return (CoreCanPlaceOnComponent) super.clone();
	}

}
