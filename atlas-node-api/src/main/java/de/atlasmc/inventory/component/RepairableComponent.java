package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.ItemType;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface RepairableComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:repairable");
	
	public static final NBTSerializationHandler<RepairableComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(RepairableComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(COMPONENT_KEY)
					.dataSetField("items", RepairableComponent::getItems, RepairableComponent::setItems, ItemType.getRegistry())
					.endComponent()
					.build();
	
	DataSet<ItemType> getItems();
	
	void setItems(DataSet<ItemType> items);
	
	RepairableComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends RepairableComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
