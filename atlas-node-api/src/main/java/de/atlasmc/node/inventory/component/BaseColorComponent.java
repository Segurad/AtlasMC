package de.atlasmc.node.inventory.component;

import de.atlasmc.node.DyeColor;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface BaseColorComponent extends ItemComponent {
	
	public static final NBTCodec<BaseColorComponent>
	NBT_HANDLER = NBTCodec
					.builder(BaseColorComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.enumStringField(ComponentType.BASE_COLOR.getNamespacedKey(), BaseColorComponent::getColor, BaseColorComponent::setColor, DyeColor.class, null)
					.build();
	
	BaseColorComponent clone();
	
	DyeColor getColor();
	
	void setColor(DyeColor color);
	
	@Override
	default NBTCodec<? extends BaseColorComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
