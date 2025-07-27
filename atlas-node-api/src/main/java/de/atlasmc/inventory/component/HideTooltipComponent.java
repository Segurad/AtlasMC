package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface HideTooltipComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:hide_tooltip");
	
	public static final NBTSerializationHandler<HideTooltipComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(HideTooltipComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(COMPONENT_KEY)
					.endComponent()
					.build();
	
	HideTooltipComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends HideTooltipComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
