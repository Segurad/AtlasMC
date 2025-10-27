package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.EnchantableComponent;

public class CoreEnchantableComponent extends AbstractItemComponent implements EnchantableComponent {
	
	private int value;
	
	public CoreEnchantableComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreEnchantableComponent clone() {
		return (CoreEnchantableComponent) super.clone();
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void setValue(int value) {
		this.value = value;
	}
	
}
