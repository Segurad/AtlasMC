package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.ItemPredicate;
import de.atlasmc.util.annotation.NotNull;

public interface LockComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<LockComponent>
	NBT_CODEC = CodecFieldCodec
				.builder(LockComponent.class, ItemPredicate.NBT_CODEC)
				.include(ItemComponent.NBT_CODEC)
				.getter(LockComponent::getPredicate)
				.setter(LockComponent::setPredicate)
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
