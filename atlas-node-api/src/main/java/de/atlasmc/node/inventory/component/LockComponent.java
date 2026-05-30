package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.ItemPredicate;
import de.atlasmc.util.annotation.NotNull;

public interface LockComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<LockComponent>
	NBT_CODEC = NBTCodec
					.builder(LockComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.LOCK.getNamespacedKey(), LockComponent::getPredicate, LockComponent::setPredicate, ItemPredicate.NBT_CODEC)
					.build();
	
	ItemPredicate getPredicate();
	
	void setPredicate(ItemPredicate predicate);
	
	@Override
	LockComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
