package de.atlasmc.inventory.component;

import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.Attributeable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface AttributeModifiersComponent extends ItemComponent, Attributeable {
	
	public static final NBTSerializationHandler<AttributeModifiersComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(AttributeModifiersComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.multimapType2TypeList(ComponentType.ATTRIBUTE_MODIFIERS.getNamespacedKey(), AttributeModifiersComponent::hasAttributeModifiers, AttributeModifiersComponent::getAttributeModifiers, "id", Attribute::getByName, AttributeModifier.NBT_HANDLER)
					.build();
	
	AttributeModifiersComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends AttributeModifiersComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
