package de.atlascore.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.EnchantmentComponent;

public class CoreEnchantmentComponent extends CoreAbstractEnchantmentComponent implements EnchantmentComponent {

	public CoreEnchantmentComponent(NamespacedKey key) {
		super(key);
	}

	@Override
	public CoreEnchantmentComponent clone() {
		return (CoreEnchantmentComponent) super.clone();
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.ENCHANTMENTS;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}

}
