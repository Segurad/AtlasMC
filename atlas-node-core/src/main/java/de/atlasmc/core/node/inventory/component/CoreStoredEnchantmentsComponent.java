package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.StoredEnchantmentComponent;

public class CoreStoredEnchantmentsComponent extends CoreAbstractEnchantmentComponent implements StoredEnchantmentComponent {

	public CoreStoredEnchantmentsComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreStoredEnchantmentsComponent clone() {
		return (CoreStoredEnchantmentsComponent) super.clone();
	}

}
