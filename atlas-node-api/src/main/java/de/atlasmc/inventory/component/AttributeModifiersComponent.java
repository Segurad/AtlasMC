package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeModifier;
import de.atlasmc.attribute.Attributeable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface AttributeModifiersComponent extends ItemComponent, Attributeable {
	
	public static final NamespacedKey COMPONENT_KEY = ComponentType.ATTRIBUTE_MODIFIERS;
	
	public static final NBTSerializationHandler<AttributeModifiersComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(AttributeModifiersComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.multimapType2TypeList(COMPONENT_KEY, AttributeModifiersComponent::hasAttributeModifiers, AttributeModifiersComponent::getAttributeModifiers, "id", Attribute::getByName, AttributeModifier.NBT_HANDLER)
					.build();
	
	AttributeModifiersComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends AttributeModifiersComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
