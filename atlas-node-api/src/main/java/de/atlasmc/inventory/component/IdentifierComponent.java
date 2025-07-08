package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

/**
 * Component that stores a {@link NamespacedKey} to identify a item.
 */
public interface IdentifierComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("atlas:identifier");
	
	public static final NBTSerializationHandler<IdentifierComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(IdentifierComponent.class)
					.namespacedKey(COMPONENT_KEY, IdentifierComponent::getIdentifier, IdentifierComponent::setIdentifier)
					.build();
	
	void setIdentifier(NamespacedKey id);
	
	NamespacedKey getIdentifier();
	
	@Override
	default NBTSerializationHandler<? extends IdentifierComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
