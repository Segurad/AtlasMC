package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.ItemType;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.dataset.DataSet;

public interface RepairableComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<RepairableComponent>
	NBT_CODEC = NBTCodec
					.builder(RepairableComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.REPAIRABLE.getNamespacedKey())
					.codec("items", RepairableComponent::getItems, RepairableComponent::setItems, DataSet.nbtCodec(ItemType.REGISTRY_KEY))
					.endComponent()
					.build();
	
	DataSet<ItemType> getItems();
	
	void setItems(DataSet<ItemType> items);
	
	@Override
	RepairableComponent clone();
	
	@Override
	default NBTCodec<? extends RepairableComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
