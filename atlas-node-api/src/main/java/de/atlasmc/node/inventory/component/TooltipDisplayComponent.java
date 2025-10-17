package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface TooltipDisplayComponent extends ItemComponent {
	
	public static final NBTCodec<TooltipDisplayComponent>
	NBT_HANDLER = NBTCodec
					.builder(TooltipDisplayComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.TOOLTIP_DISPLAY.getNamespacedKey())
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
	default NBTCodec<? extends TooltipDisplayComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
