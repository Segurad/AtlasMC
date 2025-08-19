package de.atlascore.inventory.component;

import de.atlasmc.inventory.component.CanBreakComponent;
import de.atlasmc.inventory.component.ComponentType;

public class CoreCanBreakComponent extends CoreAbstractBlockPredicateComponent implements CanBreakComponent {

	public CoreCanBreakComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreCanBreakComponent clone() {
		return (CoreCanBreakComponent) super.clone();
	}

}
