package de.atlasmc.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface TooltipStyleComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<TooltipStyleComponent>
	NBT_CODEC = NBTCodec
					.builder(TooltipStyleComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.TOOLTIP_STYLE.getNamespacedKey(), TooltipStyleComponent::getStyle, TooltipStyleComponent::setStyle, NamespacedKey.NBT_CODEC)
					.build();
	
	NamespacedKey getStyle();
	
	void setStyle(NamespacedKey style);
	
	@Override
	TooltipStyleComponent clone();
	
	@Override
	default NBTCodec<? extends TooltipStyleComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
