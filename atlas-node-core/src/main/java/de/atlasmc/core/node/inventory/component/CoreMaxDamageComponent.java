package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.MaxDamageComponent;

public class CoreMaxDamageComponent extends AbstractItemComponent implements MaxDamageComponent {

	private int maxDamage;
	
	public CoreMaxDamageComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreMaxDamageComponent clone() {
		return (CoreMaxDamageComponent) super.clone();
	}

	@Override
	public int getMaxDamage() {
		return maxDamage;
	}

	@Override
	public void setMaxDamage(int damage) {
		this.maxDamage = damage;
	}

}
