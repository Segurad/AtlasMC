package de.atlascore.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.HideTooltipComponent;
import de.atlasmc.util.annotation.Singleton;

@Singleton
public class CoreHideTooltipComponent extends AbstractItemComponent implements HideTooltipComponent {
	
	public static final CoreHideTooltipComponent INSTANCE = new CoreHideTooltipComponent(ComponentType.HIDE_TOOLTIP.getKey());
	
	public CoreHideTooltipComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreHideTooltipComponent clone() {
		return this;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.HIDE_TOOLTIP;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}

}
