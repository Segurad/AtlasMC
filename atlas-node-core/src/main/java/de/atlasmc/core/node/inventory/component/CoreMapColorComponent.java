package de.atlasmc.core.node.inventory.component;

import de.atlasmc.Color;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.MapColorComponent;

public class CoreMapColorComponent extends AbstractItemComponent implements MapColorComponent {

	private Color color;
	
	public CoreMapColorComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreMapColorComponent clone() {
		return (CoreMapColorComponent) super.clone();
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

}
