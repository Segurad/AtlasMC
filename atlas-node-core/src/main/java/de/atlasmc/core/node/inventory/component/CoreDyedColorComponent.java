package de.atlasmc.core.node.inventory.component;

import de.atlasmc.Color;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.DyedColorComponent;

public class CoreDyedColorComponent extends AbstractItemComponent implements DyedColorComponent {
	
	private Color color;
	
	public CoreDyedColorComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreDyedColorComponent clone() {
		return (CoreDyedColorComponent) super.clone();
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
