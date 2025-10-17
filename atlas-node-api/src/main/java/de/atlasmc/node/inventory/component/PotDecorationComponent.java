package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface PotDecorationComponent extends ItemComponent {
	
	public static final NBTCodec<PotDecorationComponent>
	NBT_HANDLER = NBTCodec
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
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
