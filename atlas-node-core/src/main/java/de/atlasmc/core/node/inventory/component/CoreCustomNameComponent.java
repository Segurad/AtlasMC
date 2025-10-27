package de.atlasmc.core.node.inventory.component;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.CustomNameComponent;

public class CoreCustomNameComponent extends AbstractItemComponent implements CustomNameComponent {

	protected Chat customName;
	
	public CoreCustomNameComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreCustomNameComponent clone() {
		return (CoreCustomNameComponent) super.clone();
	}

	@Override
	public Chat getCustomName() {
		return customName;
	}

	@Override
	public void setCustomName(Chat name) {
		this.customName = name;
	}

	@Override
	public boolean hasCustomName() {
		return customName != null;
	}

}
