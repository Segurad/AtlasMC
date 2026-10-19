package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.component.TrimComponent.TrimMaterial;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.annotation.NotNull;

public interface ProvidesTrimMaterialComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<ProvidesTrimMaterialComponent>
	NBT_CODEC = CodecFieldCodec
					.builder(ProvidesTrimMaterialComponent.class, Registries.registryValueNBTCodec(TrimMaterial.REGISTRY_KEY))
					.include(ItemComponent.NBT_CODEC)
					.getter(ProvidesTrimMaterialComponent::getMaterial)
					.setter(ProvidesTrimMaterialComponent::setMaterial)
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
