package de.atlasmc.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.codec.NBTCodec;

/**
 * Component that stores a {@link NamespacedKey} to identify a item.
 */
public interface IdentifierComponent extends ItemComponent {
	
	public static final NBTCodec<IdentifierComponent>
	NBT_HANDLER = NBTCodec
					.builder(IdentifierComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.namespacedKey(ComponentType.IDENTIFIER.getNamespacedKey(), IdentifierComponent::getIdentifier, IdentifierComponent::setIdentifier)
					.build();
	
	void setIdentifier(NamespacedKey id);
	
	NamespacedKey getIdentifier();
	
	IdentifierComponent clone();
	
	@Override
	default NBTCodec<? extends IdentifierComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
