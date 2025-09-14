package de.atlasmc.core.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.UseCooldownComponent;

public class CoreUseCooldownComponent extends AbstractItemComponent implements UseCooldownComponent {

	private float seconds;
	private NamespacedKey group;
	
	public CoreUseCooldownComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreUseCooldownComponent clone() {
		return (CoreUseCooldownComponent) super.clone();
	}

	@Override
	public float getSeconds() {
		return seconds;
	}

	@Override
	public void setSeconds(float seconds) {
		this.seconds = seconds;
	}

	@Override
	public NamespacedKey getGroup() {
		return group;
	}

	@Override
	public void setGroup(NamespacedKey group) {
		this.group = group;
	}

}
