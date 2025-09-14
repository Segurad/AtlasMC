package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.CanPlaceOnComponent;
import de.atlasmc.node.inventory.component.ComponentType;

public class CoreCanPlaceOnComponent extends CoreAbstractBlockPredicateComponent implements CanPlaceOnComponent {

	public CoreCanPlaceOnComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreCanPlaceOnComponent clone() {
		return (CoreCanPlaceOnComponent) super.clone();
	}

}
