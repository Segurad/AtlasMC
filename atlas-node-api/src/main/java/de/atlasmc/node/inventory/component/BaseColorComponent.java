package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.DyeColor;
import de.atlasmc.util.enums.EnumUtil;

public interface BaseColorComponent extends ItemComponent {
	
	public static final NBTCodec<BaseColorComponent>
	NBT_CODEC = NBTCodec
					.builder(BaseColorComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.BASE_COLOR.getNamespacedKey(), BaseColorComponent::getColor, BaseColorComponent::setColor, EnumUtil.enumStringNBTCodec(DyeColor.class))
					.build();
	
	public static final StreamCodec<BaseColorComponent>
	STREAM_CODEC = StreamCodec
					.builder(BaseColorComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.varIntEnum(BaseColorComponent::getColor, BaseColorComponent::setColor, DyeColor.class)
					.build();
	
	BaseColorComponent clone();
	
	DyeColor getColor();
	
	void setColor(DyeColor color);
	
	@Override
	default NBTCodec<? extends BaseColorComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends BaseColorComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
