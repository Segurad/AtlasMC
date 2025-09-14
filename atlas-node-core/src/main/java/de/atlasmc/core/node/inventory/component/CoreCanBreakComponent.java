package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.CanBreakComponent;
import de.atlasmc.node.inventory.component.ComponentType;

public class CoreCanBreakComponent extends CoreAbstractBlockPredicateComponent implements CanBreakComponent {

	public CoreCanBreakComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreCanBreakComponent clone() {
		return (CoreCanBreakComponent) super.clone();
	}

}
