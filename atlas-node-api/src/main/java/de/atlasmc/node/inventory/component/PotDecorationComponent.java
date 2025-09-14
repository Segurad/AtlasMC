package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface PotDecorationComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<PotDecorationComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(PotDecorationComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.namespacedKeyListField(ComponentType.POT_DECORATIONS.getNamespacedKey(), PotDecorationComponent::hasDecorations, PotDecorationComponent::getDecorations)
					.build();
	
	List<NamespacedKey> getDecorations();
	
	boolean hasDecorations();
	
	void addDecoration(NamespacedKey key);
	
	void removeDecoration(NamespacedKey key);
	
	PotDecorationComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends ItemComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
