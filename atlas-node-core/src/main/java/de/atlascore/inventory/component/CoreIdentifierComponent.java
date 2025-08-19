package de.atlascore.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.IdentifierComponent;

public class CoreIdentifierComponent extends AbstractItemComponent implements IdentifierComponent {

	private NamespacedKey identifier;
	
	public CoreIdentifierComponent(ComponentType type) {
		super(type);
	}

	@Override
	public void setIdentifier(NamespacedKey id) {
		identifier = id;
	}
	
	@Override
	public CoreIdentifierComponent clone() {
		return (CoreIdentifierComponent) super.clone();
	}

	@Override
	public NamespacedKey getIdentifier() {
		return identifier;
	}

}
