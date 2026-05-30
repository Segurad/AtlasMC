package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.component.TrimComponent.TrimMaterial;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.annotation.NotNull;

public interface ProvidesTrimMaterialComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<ProvidesTrimMaterialComponent>
	NBT_CODEC = NBTCodec
					.builder(ProvidesTrimMaterialComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codec(ComponentType.PROVIDES_TRIM_MATERIAL.getNamespacedKey(), ProvidesTrimMaterialComponent::getMaterial, ProvidesTrimMaterialComponent::setMaterial, Registries.registryValueNBTCodec(TrimMaterial.REGISTRY_KEY))
					.build();
	
	TrimMaterial getMaterial();
	
	void setMaterial(TrimMaterial material);
	
	@Override
	ProvidesTrimMaterialComponent clone();

	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
