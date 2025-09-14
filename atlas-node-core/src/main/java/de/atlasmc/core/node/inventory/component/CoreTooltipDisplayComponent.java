package de.atlasmc.core.node.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.TooltipDisplayComponent;

public class CoreTooltipDisplayComponent extends AbstractItemComponent implements TooltipDisplayComponent {

	private boolean hideTooltip;
	private List<ComponentType> hiddenComponents;
	
	public CoreTooltipDisplayComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreTooltipDisplayComponent clone() {
		CoreTooltipDisplayComponent clone = (CoreTooltipDisplayComponent) super.clone();
		if (hasHiddenComponents()) {
			clone.hiddenComponents = new ArrayList<>(hiddenComponents);
		}
		return clone;
	}

	@Override
	public boolean isHideTooltip() {
		return hideTooltip;
	}

	@Override
	public void setHideTooltip(boolean hide) {
		this.hideTooltip = hide;
	}

	@Override
	public List<ComponentType> getHiddenComponents() {
		if (hiddenComponents == null)
			hiddenComponents = new ArrayList<>();
		return hiddenComponents;
	}

	@Override
	public boolean hasHiddenComponents() {
		return hiddenComponents != null && !hiddenComponents.isEmpty();
	}

}
