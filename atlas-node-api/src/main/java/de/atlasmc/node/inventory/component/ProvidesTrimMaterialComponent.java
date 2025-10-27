package de.atlasmc.node.inventory.component;

import de.atlasmc.node.inventory.component.TrimComponent.TrimMaterial;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ProvidesTrimMaterialComponent extends ItemComponent {
	
	public static final NBTCodec<ProvidesTrimMaterialComponent>
	NBT_HANDLER = NBTCodec
					.builder(ProvidesTrimMaterialComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.registryValue(ComponentType.PROVIDES_TRIM_MATERIAL.getNamespacedKey(), ProvidesTrimMaterialComponent::getMaterial, ProvidesTrimMaterialComponent::setMaterial, TrimMaterial.REGISTRY_KEY)
					.build();
	
	TrimMaterial getMaterial();
	
	void setMaterial(TrimMaterial material);
	
	ProvidesTrimMaterialComponent clone();

	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
