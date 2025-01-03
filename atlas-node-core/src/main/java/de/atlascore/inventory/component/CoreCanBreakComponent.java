package de.atlascore.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.CanBreakComponent;
import de.atlasmc.inventory.component.ComponentType;

public class CoreCanBreakComponent extends CoreAbstractBlockPredicateComponent implements CanBreakComponent {

	public CoreCanBreakComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreCanBreakComponent clone() {
		return (CoreCanBreakComponent) super.clone();
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.CAN_BREAK;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}

}
