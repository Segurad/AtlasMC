package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.DyeColor;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.BaseColorComponent;
import de.atlasmc.node.inventory.component.ComponentType;

public class CoreBaseColorComponent extends AbstractItemComponent implements BaseColorComponent {

	private DyeColor color;
	
	public CoreBaseColorComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreBaseColorComponent clone() {
		return (CoreBaseColorComponent) super.clone();
	}

	@Override
	public DyeColor getColor() {
		return color;
	}

	@Override
	public void setColor(DyeColor color) {
		this.color = color;
	}

}
