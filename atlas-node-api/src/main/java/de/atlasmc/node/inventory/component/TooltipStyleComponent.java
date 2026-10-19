package de.atlasmc.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.util.annotation.NotNull;

public interface TooltipStyleComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<TooltipStyleComponent>
	NBT_CODEC = CodecFieldCodec
			.builder(TooltipStyleComponent.class, NamespacedKey.NBT_CODEC)
			.include(ItemComponent.NBT_CODEC)
			.getter(TooltipStyleComponent::getStyle)
			.setter(TooltipStyleComponent::setStyle)
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
