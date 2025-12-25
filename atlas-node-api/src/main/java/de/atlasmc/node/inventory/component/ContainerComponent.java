package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.ItemType;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public interface ContainerComponent extends ItemComponent {
	
	public static final NBTCodec<ContainerComponent>
	NBT_CODEC = NBTCodec
					.builder(ContainerComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.typeListSearchIntIndexField(ComponentType.CONTAINER.getNamespacedKey(), "slot", ContainerComponent::hasItems, ContainerComponent::getItems, ItemStack.NBT_CODEC)
					.build();
	
	@NotNull
	Int2ObjectMap<ItemStack> getItems();
	
	boolean hasItems();
	
	@Nullable
	ItemStack setItem(int slot, ItemStack item);
	
	@Nullable
	ItemStack removeItem(int slot);
	
	boolean removeItem(ItemStack item);
	
	boolean removeItem(ItemType type);
	
	ContainerComponent clone();
	
	@Override
	default NBTCodec<? extends ContainerComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
