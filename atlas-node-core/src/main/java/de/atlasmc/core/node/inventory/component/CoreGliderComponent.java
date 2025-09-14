package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.GliderComponent;
import de.atlasmc.util.annotation.Singleton;

@Singleton
public class CoreGliderComponent extends AbstractItemComponent implements GliderComponent {
	
	public CoreGliderComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreGliderComponent clone() {
		return this;
	}

}
