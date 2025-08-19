package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface TooltipDisplayComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<TooltipDisplayComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(TooltipDisplayComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.TOOLTIP_DISPLAY)
					.boolField("hide_tooltip", TooltipDisplayComponent::isHideTooltip, TooltipDisplayComponent::setHideTooltip, false)
					.registryValueList("hidden_components", TooltipDisplayComponent::hasHiddenComponents, TooltipDisplayComponent::getHiddenComponents, ComponentType.REGISTRY_KEY)
					.endComponent()
					.build();
	
	boolean isHideTooltip();
	
	void setHideTooltip(boolean hide);
	
	List<ComponentType> getHiddenComponents();
	
	boolean hasHiddenComponents();
	
	TooltipDisplayComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends TooltipDisplayComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
