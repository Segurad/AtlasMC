package de.atlascore.inventory.component;

import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.StoredEnchantmentComponent;

public class CoreStoredEnchantmentsComponent extends CoreAbstractEnchantmentComponent implements StoredEnchantmentComponent {

	public CoreStoredEnchantmentsComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreStoredEnchantmentsComponent clone() {
		return (CoreStoredEnchantmentsComponent) super.clone();
	}

}
