package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.nbt.codec.CodecListFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface PotDecorationComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<PotDecorationComponent>
	NBT_CODEC = CodecListFieldCodec
				.builder(PotDecorationComponent.class, NamespacedKey.NBT_CODEC)
				.include(ItemComponent.NBT_CODEC)
				.hasData(PotDecorationComponent::hasDecorations)
				.getter(PotDecorationComponent::getDecorations)
				.build();
	
	List<NamespacedKey> getDecorations();
	
	boolean hasDecorations();
	
	void addDecoration(NamespacedKey key);
	
	void removeDecoration(NamespacedKey key);
	
	@Override
	PotDecorationComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
