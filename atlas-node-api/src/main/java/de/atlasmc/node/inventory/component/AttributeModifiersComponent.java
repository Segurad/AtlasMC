package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.CodecCollectionFieldCodec;
import de.atlasmc.nbt.codec.MultimapTypeToCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.attribute.Attribute;
import de.atlasmc.node.attribute.AttributeModifier;
import de.atlasmc.node.attribute.Attributeable;
import de.atlasmc.util.annotation.NotNull;

public interface AttributeModifiersComponent extends ItemComponent, Attributeable {
	
	@NotNull
	public static final NBTCodec<AttributeModifiersComponent>
	NBT_CODEC = CodecCollectionFieldCodec
				.builder(AttributeModifiersComponent.class, new MultimapTypeToCodec<>("id", Attribute::getByName, AttributeModifier.NBT_CODEC))
				.include(ItemComponent.NBT_CODEC)
				.hasData(AttributeModifiersComponent::hasAttributeModifiers)
				.getter(AttributeModifiersComponent::getAttributeModifiers)
				.build();
	
	@NotNull
	public static final StreamCodec<AttributeModifiersComponent>
	STREAM_CODEC = StreamCodec
					.builder(AttributeModifiersComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.multimapTypeToCodec(AttributeModifiersComponent::hasAttributeModifiers, AttributeModifiersComponent::getAttributeModifiers, Attribute::getByID, AttributeModifier.STREAM_CODEC)
					.build();
	
	@Override
	AttributeModifiersComponent clone();
	
	@Override
	default NBTCodec<? extends AttributeModifiersComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends AttributeModifiersComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
