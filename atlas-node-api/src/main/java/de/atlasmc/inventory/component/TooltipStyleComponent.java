package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface TooltipStyleComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<TooltipStyleComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(TooltipStyleComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.namespacedKey(ComponentType.TOOLTIP_STYLE, TooltipStyleComponent::getStyle, TooltipStyleComponent::setStyle)
					.build();
	
	NamespacedKey getStyle();
	
	void setStyle(NamespacedKey style);
	
	TooltipStyleComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends TooltipStyleComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
