package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface HideAdditionalTooltipComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:hide_additional_tooltip");
	
	public static final NBTSerializationHandler<HideAdditionalTooltipComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(HideAdditionalTooltipComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(COMPONENT_KEY)
					.endComponent()
					.build();
	
	HideAdditionalTooltipComponent clone();

	@Override
	default NBTSerializationHandler<? extends HideAdditionalTooltipComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
