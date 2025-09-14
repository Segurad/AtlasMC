package de.atlasmc.core.node.inventory.component;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.ItemNameComponent;

public class CoreItemNameComponent extends AbstractItemComponent implements ItemNameComponent {

	private Chat name;
	
	public CoreItemNameComponent(ComponentType type) {
		super(type);
	}

	@Override
	public Chat getName() {
		return name;
	}

	@Override
	public void setName(Chat name) {
		this.name = name;
	}
	
	@Override
	public CoreItemNameComponent clone() {
		return (CoreItemNameComponent) super.clone();
	}

}
