package de.atlascore.inventory.component;

import de.atlasmc.chat.Chat;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.ItemNameComponent;

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
