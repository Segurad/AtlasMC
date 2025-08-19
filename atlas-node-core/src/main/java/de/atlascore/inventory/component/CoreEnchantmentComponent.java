package de.atlascore.inventory.component;

import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.EnchantmentComponent;

public class CoreEnchantmentComponent extends CoreAbstractEnchantmentComponent implements EnchantmentComponent {

	public CoreEnchantmentComponent(ComponentType type) {
		super(type);
	}

	@Override
	public CoreEnchantmentComponent clone() {
		return (CoreEnchantmentComponent) super.clone();
	}

}
