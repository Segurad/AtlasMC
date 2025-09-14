package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.EnchantmentComponent;

public class CoreEnchantmentComponent extends CoreAbstractEnchantmentComponent implements EnchantmentComponent {

	public CoreEnchantmentComponent(ComponentType type) {
		super(type);
	}

	@Override
	public CoreEnchantmentComponent clone() {
		return (CoreEnchantmentComponent) super.clone();
	}

}
