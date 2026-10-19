package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.CodecListFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.annotation.NotNull;

public interface ChargedProjectilesComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<ChargedProjectilesComponent>
	NBT_CODEC = CodecListFieldCodec
				.builder(ChargedProjectilesComponent.class, ItemStack.NBT_CODEC)
				.include(ItemComponent.NBT_CODEC)
				.hasData(ChargedProjectilesComponent::hasProjectiles)
				.getter(ChargedProjectilesComponent::getProjectiles)
				.build();
	
	@NotNull
	public static final StreamCodec<ChargedProjectilesComponent>
	STREAM_CODEC = StreamCodec
					.builder(ChargedProjectilesComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codecList(ChargedProjectilesComponent::hasProjectiles, ChargedProjectilesComponent::getProjectiles, ItemStack.STREAM_CODEC)
					.build();
	
	List<ItemStack> getProjectiles();
	
	boolean hasProjectiles();
	
	void addProjectile(ItemStack item);
	
	void removeProjectile(ItemStack item);
	
	@Override
	ChargedProjectilesComponent clone();
	
	@Override
	default NBTCodec<? extends ChargedProjectilesComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends ChargedProjectilesComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
