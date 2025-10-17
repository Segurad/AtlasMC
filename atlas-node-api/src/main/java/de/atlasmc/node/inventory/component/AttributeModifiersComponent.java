package de.atlasmc.node.inventory.component;

import de.atlasmc.node.attribute.Attribute;
import de.atlasmc.node.attribute.AttributeModifier;
import de.atlasmc.node.attribute.Attributeable;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface AttributeModifiersComponent extends ItemComponent, Attributeable {
	
	public static final NBTCodec<AttributeModifiersComponent>
	NBT_HANDLER = NBTCodec
					.builder(AttributeModifiersComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.multimapType2TypeList(ComponentType.ATTRIBUTE_MODIFIERS.getNamespacedKey(), AttributeModifiersComponent::hasAttributeModifiers, AttributeModifiersComponent::getAttributeModifiers, "id", Attribute::getByName, AttributeModifier.NBT_HANDLER)
					.build();
	
	AttributeModifiersComponent clone();
	
	@Override
	default NBTCodec<? extends AttributeModifiersComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
