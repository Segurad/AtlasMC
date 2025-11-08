package de.atlasmc.node.inventory.component;

import de.atlasmc.Color;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;

public interface DyedColorComponent extends ItemComponent {
	
	public static final NBTCodec<DyedColorComponent>
	NBT_CODEC = NBTCodec
					.builder(DyedColorComponent.class)
					.codec(ComponentType.DYED_COLOR.getNamespacedKey(), DyedColorComponent::getColor, DyedColorComponent::setColor, Color.NBT_CODEC)
					.build();
	
	public static final StreamCodec<DyedColorComponent>
	STREAM_CODEC = StreamCodec
					.builder(DyedColorComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.intToObject(DyedColorComponent::getColor, DyedColorComponent::setColor, Color::fromRGB, Color::asRGB)
					.build();
	
	Color getColor();
	
	void setColor(Color color);
	
	DyedColorComponent clone();
	
	@Override
	default NBTCodec<? extends DyedColorComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends DyedColorComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
