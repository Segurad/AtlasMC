package de.atlasmc.node.inventory.component;

import de.atlasmc.node.inventory.component.TrimComponent.TrimMaterial;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ProvidesTrimMaterialComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<ProvidesTrimMaterialComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ProvidesTrimMaterialComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.registryValue(ComponentType.PROVIDES_TRIM_MATERIAL.getNamespacedKey(), ProvidesTrimMaterialComponent::getMaterial, ProvidesTrimMaterialComponent::setMaterial, TrimMaterial.REGISTRY_KEY)
					.build();
	
	TrimMaterial getMaterial();
	
	void setMaterial(TrimMaterial material);
	
	ProvidesTrimMaterialComponent clone();

	@Override
	default NBTSerializationHandler<? extends ItemComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
