package de.atlasmc.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

/**
 * Component that stores a {@link NamespacedKey} to identify a item.
 */
public interface IdentifierComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<IdentifierComponent>
	NBT_CODEC = CodecFieldCodec
				.builder(IdentifierComponent.class, NamespacedKey.NBT_CODEC)
				.include(ItemComponent.NBT_CODEC)
				.getter(IdentifierComponent::getIdentifier)
				.setter(IdentifierComponent::setIdentifier)
				.build();
	
	void setIdentifier(NamespacedKey id);
	
	NamespacedKey getIdentifier();
	
	@Override
	IdentifierComponent clone();
	
	@Override
	default NBTCodec<? extends IdentifierComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
