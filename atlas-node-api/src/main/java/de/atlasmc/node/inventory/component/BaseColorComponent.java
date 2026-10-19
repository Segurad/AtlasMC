package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.DyeColor;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumUtil;

public interface BaseColorComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<BaseColorComponent>
	NBT_CODEC = CodecFieldCodec
				.builder(BaseColorComponent.class, EnumUtil.enumStringNBTCodec(DyeColor.class))
				.include(ItemComponent.NBT_CODEC)
				.getter(BaseColorComponent::getColor)
				.setter(BaseColorComponent::setColor)
				.build();
	
	@NotNull
	public static final StreamCodec<BaseColorComponent>
	STREAM_CODEC = StreamCodec
					.builder(BaseColorComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.varIntEnum(BaseColorComponent::getColor, BaseColorComponent::setColor, DyeColor.class)
					.build();
	
	@Override
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
