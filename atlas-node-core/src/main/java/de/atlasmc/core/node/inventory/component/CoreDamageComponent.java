package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.DamageComponent;

public class CoreDamageComponent extends AbstractItemComponent implements DamageComponent {
	
	private int damage;
	
	public CoreDamageComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreDamageComponent clone() {
		return (CoreDamageComponent) super.clone();
	}

	@Override
	public int getDamage() {
		return damage;
	}

	@Override
	public void setDamage(int damage) {
		this.damage = damage;
	}

}
