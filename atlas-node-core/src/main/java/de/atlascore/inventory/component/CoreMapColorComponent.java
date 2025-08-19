package de.atlascore.inventory.component;

import de.atlasmc.Color;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.MapColorComponent;

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
