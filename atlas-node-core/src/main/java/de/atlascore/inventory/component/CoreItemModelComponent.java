package de.atlascore.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.ItemModelComponent;

public class CoreItemModelComponent extends AbstractItemComponent implements ItemModelComponent {

	private NamespacedKey model;
	
	public CoreItemModelComponent(ComponentType type) {
		super(type);
	}

	@Override
	public NamespacedKey getModel() {
		return model;
	}

	@Override
	public void setModel(NamespacedKey model) {
		this.model = model;
	}
	
	@Override
	public CoreItemModelComponent clone() {
		return (CoreItemModelComponent) super.clone();
	}

}
