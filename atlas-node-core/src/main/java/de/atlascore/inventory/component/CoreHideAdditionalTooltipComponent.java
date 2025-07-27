package de.atlascore.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.HideAdditionalTooltipComponent;
import de.atlasmc.util.annotation.Singleton;

@Singleton
public class CoreHideAdditionalTooltipComponent extends AbstractItemComponent implements HideAdditionalTooltipComponent {
	
	public static final CoreHideAdditionalTooltipComponent INSTANCE = new CoreHideAdditionalTooltipComponent(ComponentType.HIDE_ADDITIONAL_TOOLTIP.getKey());
	
	public CoreHideAdditionalTooltipComponent(NamespacedKey key) {
		super(key);
	}
	
	@Override
	public CoreHideAdditionalTooltipComponent clone() {
		return this;
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.HIDE_ADDITIONAL_TOOLTIP;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}

}
