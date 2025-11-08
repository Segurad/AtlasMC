package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.registry.Registries;

public interface TooltipDisplayComponent extends ItemComponent {
	
	public static final NBTCodec<TooltipDisplayComponent>
	NBT_HANDLER = NBTCodec
					.builder(TooltipDisplayComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.TOOLTIP_DISPLAY.getNamespacedKey())
					.boolField("hide_tooltip", TooltipDisplayComponent::isHideTooltip, TooltipDisplayComponent::setHideTooltip, false)
					.codecList("hidden_components", TooltipDisplayComponent::hasHiddenComponents, TooltipDisplayComponent::getHiddenComponents, Registries.registryValueNBTCodec(ComponentType.REGISTRY_KEY))
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
