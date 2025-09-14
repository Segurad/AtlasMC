package de.atlasmc.core.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.TooltipStyleComponent;

public class CoreTooltipStyleComponent extends AbstractItemComponent implements TooltipStyleComponent {

	private NamespacedKey style;
	
	public CoreTooltipStyleComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreTooltipStyleComponent clone() {
		return (CoreTooltipStyleComponent) super.clone();
	}

	@Override
	public NamespacedKey getStyle() {
		return style;
	}

	@Override
	public void setStyle(NamespacedKey style) {
		this.style = style;
	}

}
