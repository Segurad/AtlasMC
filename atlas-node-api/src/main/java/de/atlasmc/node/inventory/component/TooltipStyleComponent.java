package de.atlasmc.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.nbt.codec.NBTCodec;

public interface TooltipStyleComponent extends ItemComponent {
	
	public static final NBTCodec<TooltipStyleComponent>
	NBT_HANDLER = NBTCodec
					.builder(TooltipStyleComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.TOOLTIP_STYLE.getNamespacedKey(), TooltipStyleComponent::getStyle, TooltipStyleComponent::setStyle, NamespacedKey.NBT_CODEC)
					.build();
	
	NamespacedKey getStyle();
	
	void setStyle(NamespacedKey style);
	
	TooltipStyleComponent clone();
	
	@Override
	default NBTCodec<? extends TooltipStyleComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
