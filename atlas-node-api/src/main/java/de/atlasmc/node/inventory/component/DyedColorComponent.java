package de.atlasmc.node.inventory.component;

import de.atlasmc.Color;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface DyedColorComponent extends ItemComponent {
	
	public static final NBTCodec<DyedColorComponent>
	NBT_HANDLER = NBTCodec
					.builder(DyedColorComponent.class)
					.color(ComponentType.DYED_COLOR.getNamespacedKey(), DyedColorComponent::getColor, DyedColorComponent::setColor)
					.build();
	
	Color getColor();
	
	void setColor(Color color);
	
	DyedColorComponent clone();
	
	@Override
	default NBTCodec<? extends DyedColorComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
