package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.attribute.Attribute;
import de.atlasmc.node.attribute.AttributeModifier;
import de.atlasmc.node.attribute.Attributeable;

public interface AttributeModifiersComponent extends ItemComponent, Attributeable {
	
	public static final NBTCodec<AttributeModifiersComponent>
	NBT_CODEC = NBTCodec
					.builder(AttributeModifiersComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.multimapTypeToCodec(ComponentType.ATTRIBUTE_MODIFIERS.getNamespacedKey(), AttributeModifiersComponent::hasAttributeModifiers, AttributeModifiersComponent::getAttributeModifiers, AttributeModifier.NBT_CODEC, "id", Attribute::getByName)
					.build();
	
	public static final StreamCodec<AttributeModifiersComponent>
	STREAM_CODEC = StreamCodec
					.builder(AttributeModifiersComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.multimapTypeToCodec(AttributeModifiersComponent::hasAttributeModifiers, AttributeModifiersComponent::getAttributeModifiers, Attribute::getByID, AttributeModifier.STREAM_CODEC)
					.build();
	
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
