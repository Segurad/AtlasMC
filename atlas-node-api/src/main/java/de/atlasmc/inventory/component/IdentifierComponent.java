package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

/**
 * Component that stores a {@link NamespacedKey} to identify a item.
 */
public interface IdentifierComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<IdentifierComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(IdentifierComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.namespacedKey(ComponentType.IDENTIFIER.getNamespacedKey(), IdentifierComponent::getIdentifier, IdentifierComponent::setIdentifier)
					.build();
	
	void setIdentifier(NamespacedKey id);
	
	NamespacedKey getIdentifier();
	
	IdentifierComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends IdentifierComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
