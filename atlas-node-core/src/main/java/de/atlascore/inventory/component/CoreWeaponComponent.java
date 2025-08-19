package de.atlascore.inventory.component;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.WeaponComponent;

public class CoreWeaponComponent extends AbstractItemComponent implements WeaponComponent {

	private int itemDamagePerAttack;
	private float disableBlockSeconds;
	
	public CoreWeaponComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreWeaponComponent clone() {
		return (CoreWeaponComponent) super.clone();
	}

	@Override
	public int getItemDamagePerAttack() {
		return itemDamagePerAttack;
	}

	@Override
	public void setItemDamagePerAttack(int damage) {
		this.itemDamagePerAttack = damage;
	}

	@Override
	public float getDisableBlockSeconds() {
		return disableBlockSeconds;
	}

	@Override
	public void setDisableBlockSeconds(float disable) {
		this.disableBlockSeconds = disable;
	}

}
