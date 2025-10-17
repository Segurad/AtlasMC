package de.atlasmc.node.inventory.component;

import de.atlasmc.node.inventory.ItemType;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface RepairableComponent extends ItemComponent {
	
	public static final NBTCodec<RepairableComponent>
	NBT_HANDLER = NBTCodec
					.builder(RepairableComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.REPAIRABLE.getNamespacedKey())
					.dataSetField("items", RepairableComponent::getItems, RepairableComponent::setItems, ItemType.getRegistry())
					.endComponent()
					.build();
	
	DataSet<ItemType> getItems();
	
	void setItems(DataSet<ItemType> items);
	
	RepairableComponent clone();
	
	@Override
	default NBTCodec<? extends RepairableComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
