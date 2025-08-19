package de.atlascore.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.TooltipStyleComponent;

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
